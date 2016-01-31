package co.johnrowley.distributed.simple;

import co.johnrowley.distributed.RabbitConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by RowleyJohn on 1/29/2016.
 */
public class RabbitProducer {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RabbitConfig.URL);
        factory.setPassword(RabbitConfig.PASSWORD);
        factory.setUsername(RabbitConfig.USERNAME);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        sendMessage(channel, "Foo");

        channel.close();
        connection.close();
    }

    private static void sendMessage(Channel channel, String message) throws IOException {
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");
    }
}
