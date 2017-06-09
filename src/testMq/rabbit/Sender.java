package testMq.rabbit;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;

import java.io.IOException;

/**
 * Created by darrenfu on 17-4-27.
 */
public class Sender {


    public static void main(String[] args) throws IOException, InterruptedException {

        AMQP.BasicProperties properties = new AMQP.BasicProperties();
        properties = properties.builder().build();
        Channel channel = RabbitMqClient.getChannel();
        channel.confirmSelect();
//        channel.basicQos(2);
//        channel.queueDeclare("queue.test", true, false, false, null);
//        channel.queueBind("queue.test", "local.direct", "key.test");
        // 消息发送到投递到队列中 则成功confirm
        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("@Confirm success!");
            }

            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("#NACK success!");
            }
        });
        System.out.println("#send msg");
        channel.basicPublish("local.direct", "key.test", properties, String.valueOf("XXXXX").getBytes());
        System.out.println("#send ok");

//        channel.waitForConfirms();

    }

    public void sendMsg() {


    }


}
