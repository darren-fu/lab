package testMq.rabbit;

import com.rabbitmq.client.*;
import com.rabbitmq.client.impl.AMQImpl;

import java.io.IOException;

/**
 * Created by darrenfu on 17-4-27.
 */
public class Consumer {


    private void consume() throws IOException {
        AMQP.BasicProperties properties = new AMQP.BasicProperties();
        Channel channel = RabbitMqClient.getClient().createChannel();
        channel.basicQos(2);
        channel.basicConsume("queue.test", false, new com.rabbitmq.client.Consumer() {
            @Override
            public void handleConsumeOk(String consumerTag) {
                System.out.println("ConsumeOK..." + consumerTag);
//                channel.basicAck(AMQImpl.Basic.GetOk.INDEX);


            }

            @Override
            public void handleCancelOk(String consumerTag) {
                System.out.println("handleCancelOk..." + consumerTag);

            }

            @Override
            public void handleCancel(String consumerTag) throws IOException {
                System.out.println("handleCancel..." + consumerTag);

            }

            @Override
            public void handleShutdownSignal(String consumerTag, ShutdownSignalException sig) {
                System.out.println("handleShutdownSignal..." + consumerTag);

            }

            @Override
            public void handleRecoverOk(String consumerTag) {
                System.out.println("handleRecoverOk..." + consumerTag);

            }

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("handleDelivery..." + consumerTag + ",msg:" + new String(body));
                System.out.println("envelope:" + envelope);
                System.out.println("properties:" + properties);

            }
        });
    }


    public static void main(String[] args) throws IOException {
        Consumer consumer = new Consumer();
        consumer.consume();
    }


}
