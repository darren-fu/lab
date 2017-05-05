package testMq.rabbit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by darrenfu on 17-4-27.
 */
public class RabbitMqClient {
    private static ConnectionFactory factory;

    static {
        factory = new ConnectionFactory();
        factory.setUsername("admin");
        factory.setPassword("123456");
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setVirtualHost("/");
    }

    /**
     * Gets client.
     *
     * @return the client
     * @throws IOException      the io exception
     * @throws TimeoutException the timeout exception
     */
    public static Connection getClient() {
        try {
            return factory.newConnection();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

//
//    Broker：简单来说就是消息队列服务器实体。
//    Exchange：消息交换机，它指定消息按什么规则，路由到哪个队列。
//    Queue：消息队列载体，每个消息都会被投入到一个或多个队列。
//    Binding：绑定，它的作用就是把exchange和queue按照路由规则绑定起来。
//    Routing Key：路由关键字，exchange根据这个关键字进行消息投递。
//    vhost：虚拟主机，一个broker里可以开设多个vhost，用作不同用户的权限分离。
//    producer：消息生产者，就是投递消息的程序。
//    consumer：消息消费者，就是接受消息的程序。
//    channel：消息通道，在客户端的每个连接里，可建立多个channel，每个channel代表一个会话任务。

//  由Exchange，Queue，RoutingKey三个才能决定一个从Exchange到Queue的唯一的线路。


    /**
     * Create queue.
     *
     * @param queueName the queue name
     */
    public static void createQueue(String queueName) {
        try {
            int channelNumber = getClient().createChannel().getChannelNumber();
            System.out.println(channelNumber);

//            getClient().createChannel().abort();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
//        RabbitMqClient.createQueue("");

        System.out.println(RabbitMqClient.getClient().getChannelMax());
        System.out.println(RabbitMqClient.getClient().getClientProvidedName());
        System.out.println(RabbitMqClient.getClient().getClientProperties());
        System.out.println(RabbitMqClient.getClient().getExceptionHandler());

        System.out.println(RabbitMqClient.getClient().getServerProperties());
        System.out.println(RabbitMqClient.getClient().getId());
        System.out.println(RabbitMqClient.getClient().getHeartbeat());


        Connection client = RabbitMqClient.getClient();
        Channel channel = client.createChannel();

        Connection conn2 = channel.getConnection();
        System.out.println(conn2 == client);
        System.out.println(conn2.hashCode() );
        System.out.println(client.hashCode());

    }

}
