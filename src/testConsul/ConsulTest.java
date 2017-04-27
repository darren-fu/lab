package testConsul;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.QueryParams;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.health.model.Check;
import com.ecwid.consul.v1.health.model.HealthService;

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

    }


    public void removeAllCriticalService(){
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
