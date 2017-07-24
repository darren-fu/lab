package testConsul;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.QueryParams;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.agent.model.NewService;
import com.ecwid.consul.v1.catalog.model.CatalogService;
import com.ecwid.consul.v1.health.model.Check;
import com.ecwid.consul.v1.health.model.HealthService;
import com.google.code.ssm.mapper.JsonObjectMapper;
import org.junit.Test;
import util.JsonMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * author: fuliang
 * date: 2017/4/27
 */
public class ConsulTest {


    private static ConsulClient consulClient;

    static {
        String host = "192.168.1.248";
        int port = 8500;

        consulClient = new ConsulClient(host, port);

    }

    public static void main(String[] args) {
        ConsulTest consulTest = new ConsulTest();
        consulTest.removeAllCriticalService();
//        System.out.println(QueryParams.DEFAULT);
//        Response<List<CatalogService>> catalogService = consulClient.getCatalogService("my-service", QueryParams.DEFAULT);
//        System.out.println(catalogService);
//        QueryParams queryParams = new QueryParams(50000,60);
//        Response<List<Check>> healthChecksState = consulClient.getHealthChecksState(Check.CheckStatus.CRITICAL, queryParams);
//        System.out.println(JsonMapper.defaultMapper().toPrettyJson(healthChecksState));

//        consulTest.registerNodeService();
//        consulTest.registerNodeService2();
    }


    public void registerNodeService() {
        NewService service = new NewService();
        service.setId("my-service-3000");
        service.setName("my-service");
        service.setAddress("127.0.0.1");
        service.setPort(3000);
        service.setTags(Collections.singletonList("weight=4"));

        NewService.Check check = new NewService.Check();
        check.setHttp("http://localhost:3000/health");
        check.setInterval("20s");
        service.setCheck(check);
        consulClient.agentServiceRegister(service);

//        registerNodeService();
//        registerNodeService2();
    }


    public void registerNodeService2() {
        NewService service = new NewService();
        service.setId("my-service-3100");
        service.setName("my-service");
        service.setAddress("127.0.0.1");
        List<String> tags = new ArrayList<>();
        tags.add("weight=3");
        tags.add("backup");
        service.setTags(tags);

        service.setPort(3100);
        NewService.Check check = new NewService.Check();
        check.setHttp("http://localhost:3100/health");
        check.setInterval("20s");
        service.setCheck(check);
        consulClient.agentServiceRegister(service);
    }

    @Test
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
