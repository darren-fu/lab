package testMq.rabbit;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

import java.io.IOException;

/**
 * Created by darrenfu on 17-4-27.
 */
public class Sender {


    private void getClient() {


    }


    public static void main(String[] args) throws IOException {

        AMQP.BasicProperties properties = new AMQP.BasicProperties();
        properties = properties.builder().build();
        Channel channel = RabbitMqClient.getClient().createChannel();
        channel.basicQos(2);
        channel.queueDeclare("queue.test", true, false, false, null);
        channel.queueBind("queue.test", "local.direct", "key.test");

        channel.basicPublish("local.direct", "key.test", properties, String.valueOf("XXXXX").getBytes());
    }

    public void sendMsg() {


    }


}
