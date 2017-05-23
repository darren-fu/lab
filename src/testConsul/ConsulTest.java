package testConsul;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.QueryParams;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.agent.model.NewService;
import com.ecwid.consul.v1.catalog.model.CatalogService;
import com.ecwid.consul.v1.health.model.Check;
import com.ecwid.consul.v1.health.model.HealthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

/**
 * author: fuliang
 * date: 2017/4/27
 */
public class ConsulTest {


    private static ConsulClient consulClient;

    static {
        String host = "localhost";
        int port = 8500;

        consulClient = new ConsulClient(host, port);

    }

    public static void main(String[] args) throws JsonProcessingException {
        ConsulTest consulTest = new ConsulTest();
//        consulTest.removeAllCriticalService();
        System.out.println(QueryParams.DEFAULT);
        Response<List<CatalogService>> catalogService = consulClient.getCatalogService("my-service", QueryParams.DEFAULT);
        System.out.println(catalogService);
        QueryParams queryParams = new QueryParams(50000,60);
        Response<List<Check>> healthChecksState = consulClient.getHealthChecksState(Check.CheckStatus.CRITICAL, queryParams);

        ObjectMapper mapper = new ObjectMapper();

        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(healthChecksState));
    }


    public void registerNodeService() {
        NewService service = new NewService();
        service.setName("my-service");
        service.setAddress("127.0.0.1");
        service.setPort(3000);
        NewService.Check check = new NewService.Check();
        check.setHttp("http://localhost:3000/health");
        check.setInterval("20s");
        service.setCheck(check);
        consulClient.agentServiceRegister(service);
    }

    public void removeAllCriticalService() {
        Response<Map<String, List<String>>> catalogServices = consulClient.getCatalogServices(QueryParams.DEFAULT);
        for (String serviceName : catalogServices.getValue().keySet()) {
            removeCriticalServiceId(serviceName);
            Response<List<HealthService>> healthServices = consulClient.getHealthServices(serviceName, true, QueryParams.DEFAULT);
            for (HealthService healthService : healthServices.getValue()) {
                System.out.println(healthService);
            }
        }
    }

    public void removeCriticalServiceId(String serviceName) {
        String service = serviceName;

        Response<List<Check>> marketingService = consulClient.getHealthChecksForService(service, QueryParams.DEFAULT);

        for (Check check : marketingService.getValue()) {

            System.out.println(check);
            System.out.println(check.getServiceId() + ":" + check.getCheckId() + ":" + check.getStatus());

            if (Check.CheckStatus.CRITICAL.equals(check.getStatus())) {
                consulClient.agentServiceDeregister(check.getServiceId());
                System.out.println("remove:" + check.getServiceId());
            }
        }
    }
}
