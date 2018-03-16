package testMq.rabbit;

import com.rabbitmq.client.*;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by darrenfu on 17-4-27.
 */
public class ConsumerTest {

    private static String queue1 = "queue.direct";
    private static String queue2 = "queue.test";

    public static void main(String[] args) throws IOException {

        ExecutorService service = Executors.newFixedThreadPool(5);

        // 同一个队列的消费者轮训获取message
        service.submit(() -> {
            ConsumerTest.consumeMsg2(queue2);
        });
        service.submit(() -> {
            ConsumerTest.consumeMsg2(queue2);
        });
    }


    @Test
    public void test111() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(5);
        service.submit(() -> {
            ConsumerTest.consumeMsg2(queue2);
        });
        Thread.sleep(100000L * 30);
    }
    @Test
    public void test222() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(5);
        service.submit(() -> {
            ConsumerTest.consumeMsg3(queue2);
        });
        Thread.sleep(100000L * 30);
    }

    public static void consumeMsg(String queue) {
        while (true) {
            try {
                Channel channel = RabbitMqClient.getChannel();
                SimpleConsumer simpleConsumer = new SimpleConsumer(channel);
                channel.addConfirmListener(new ConfirmListener() {
                    @Override
                    public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                        System.out.println("@@@ACK success!");
                    }

                    @Override
                    public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                        System.out.println("###NACK success!");
                    }
                });
                channel.basicQos(1);
                channel.basicConsume(queue, false, simpleConsumer);

//            while (true) {
////                channel.
////                simpleConsumer
//                Thread.sleep(1000);
//            }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void consumeMsg2(String queue) {
        Channel channel = RabbitMqClient.getChannel();

        boolean autoAck = false;
        try {
            channel.basicQos(1);
            channel.basicConsume(queue, autoAck, Thread.currentThread().getName(),
                    new DefaultConsumer(channel) {
                        @Override
                        public void handleDelivery(String consumerTag,
                                                   Envelope envelope,
                                                   AMQP.BasicProperties properties,
                                                   byte[] body)
                                throws IOException {
                            String routingKey = envelope.getRoutingKey();
                            String contentType = properties.getContentType();
                            long deliveryTag = envelope.getDeliveryTag();
                            // (process the message components here ...)
                            System.out.println(channel.getChannelNumber() + ":msg:" + new String(body));

                            channel.basicAck(deliveryTag, false);
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void consumeMsg3(String queue) {
        Channel channel = RabbitMqClient.getChannel();

        boolean autoAck = false;
        try {
            channel.basicQos(1);
            channel.basicConsume(queue, autoAck, Thread.currentThread().getName(),
                    new DefaultConsumer(channel) {
                        @Override
                        public void handleDelivery(String consumerTag,
                                                   Envelope envelope,
                                                   AMQP.BasicProperties properties,
                                                   byte[] body)
                                throws IOException {
                            String routingKey = envelope.getRoutingKey();
                            String contentType = properties.getContentType();
                            long deliveryTag = envelope.getDeliveryTag();
                            // (process the message components here ...)
                            System.out.println(channel.getChannelNumber() + ":msg:" + new String(body));

//                            channel.basicAck(deliveryTag, false);
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void consumeQueueMsg(String queue) {
        Channel channel = RabbitMqClient.getChannel();
        QueueingConsumer consumer = new QueueingConsumer(channel);

        while (true) {
            try {
                channel.basicConsume(queue, false, consumer);
                channel.basicQos(1);
//                channel.
                QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                Thread.sleep(1000);
                System.out.println(channel.getChannelNumber() + ":msg:" + new String(delivery.getBody()));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private static class SimpleConsumer implements Consumer {
        private Channel channel;

        private SimpleConsumer(Channel channel) {
            this.channel = channel;
        }

        @Override
        public void handleConsumeOk(String consumerTag) {
//            System.out.println("ConsumeOK..." + consumerTag);
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

            System.out.println("========================" + Thread.currentThread().getName() + "=======================");
            System.out.println("handleDelivery..." + consumerTag + ",msg:" + new String(body));
            System.out.println("envelope:" + envelope);
            System.out.println("properties:" + properties);
//            try {
//                Thread.sleep(10000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            //channel.basicAck(envelope.getDeliveryTag(), false);
            //  System.out.println("=====================ACK : " + new String(body) + "==========================");

        }
    }

}
