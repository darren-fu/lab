
AMQP
---
1. Broker：简单来说就是消息队列服务器实体。
2. Exchange：消息交换机，它指定消息按什么规则，路由到哪个队列。
3. Queue：消息队列载体，每个消息都会被投入到一个或多个队列。
4. Binding：绑定，它的作用就是把exchange和queue按照路由规则绑定起来。
5. Routing Key：路由关键字，exchange根据这个关键字进行消息投递。
6. vhost：虚拟主机，一个broker里可以开设多个vhost，用作不同用户的权限分离。
7. producer：消息生产者，就是投递消息的程序。
8. consumer：消息消费者，就是接受消息的程序。
9. channel：消息通道，在客户端的每个连接里，可建立多个channel，每个channel代表一个会话任务。
> 由Exchange，Queue，RoutingKey三个才能决定一个从Exchange到Queue的唯一的线路。

#### 消息流程
* 客户端连接到消息队列服务器，打开一个channel。
* 客户端声明一个exchange，并设置相关属性。
* 客户端声明一个queue，并设置相关属性。
* 客户端使用routing key，在exchange和queue之间建立好绑定关系。
* 客户端投递消息到exchange。
* exchange接收到消息后，就根据消息的key和已经设置的binding，进行消息路由，将消息投递到一个或多个队列里.
* exchange也有几个类型，完全根据key进行投递的叫做Direct交换机，
例如，绑定时设置了routing key为”abc”，那么客户端提交的消息，只有设置了key为”abc”的才会投递到队列。
对key进行模式匹配后进行投递的叫做Topic交换机，符号”#”匹配一个或多个词，符号”\*”匹配正好一个词。
例如”abc.#”匹配”abc.def.ghi”，”abc.\*”只匹配”abc.def”。
还有一种不需要key的，叫做Fanout交换机，它采取广播模式，一个消息进来时，投递到与该交换机绑定的所有队列。

#### 消息持久化
RabbitMQ支持消息的持久化，也就是数据写在磁盘上，为了数据安全考虑，我想大多数用户都会选择持久化。消息队列持久化包括3个部分：
* exchange持久化，在声明时指定durable => 1
* queue持久化，在声明时指定durable => 1
* 消息持久化，在投递时指定delivery_mode=> 2（1是非持久化） 

如果exchange和queue都是持久化的，那么它们之间的binding也是持久化的。如果exchange和queue两者之间有一个持久化，一个非持久化，就不允许建立绑定。

#### 常用命令
1.停止RabbitMQ应用，关闭节点 
 ```
 # rabbitmqctl stop 
 ```
2.停止RabbitMQ应用 
```
# rabbitmqctl stop_app
```
3.启动RabbitMQ应用
```
# rabbitmqctl start_app
```
4.显示RabbitMQ中间件各种信息
```
# rabbitmqctl status
```
5.重置RabbitMQ节点
```
# rabbitmqctl reset
# rabbitmqctl force_reset
```
