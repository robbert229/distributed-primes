package co.johnrowley.distributed.prime;

import co.johnrowley.distributed.RabbitConfig;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by RowleyJohn on 1/30/2016.
 */
public class PrimeMaster {
    public static final long MAX = 2140000000;
    public static final long MIN = MAX - 1000000;

    public static void main(String[] argv) throws IOException, TimeoutException {
        new PrimeMaster();
    }

    public PrimeMaster() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RabbitConfig.URL);
        factory.setPassword(RabbitConfig.PASSWORD);
        factory.setUsername(RabbitConfig.USERNAME);

        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();
        channel.queueDeclare(PrimesConfig.QUEUE, false, false, false, null);

        for(long i = MIN; i < MAX; i++) {
            String message = new Long(i).toString();
            channel.basicPublish("", PrimesConfig.QUEUE, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        channel.close();
        connection.close();
    }
}
