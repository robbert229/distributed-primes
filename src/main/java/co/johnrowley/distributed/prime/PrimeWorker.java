package co.johnrowley.distributed.prime;

import co.johnrowley.distributed.RabbitConfig;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeoutException;

/**
 * Created by RowleyJohn on 1/30/2016.
 */
public class PrimeWorker {
    public static void main(String[] argv) throws IOException, TimeoutException {
        new PrimeWorker();
    }

    public PrimeWorker() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RabbitConfig.URL);
        factory.setPassword(RabbitConfig.PASSWORD);
        factory.setUsername(RabbitConfig.USERNAME);

        Connection connection = factory.newConnection();

        final Channel channel = connection.createChannel();
        final PrimeCalculator pc = new PrimeCalculator();

        System.out.println(" [x] Awaiting Prime requests");

        channel.queueDeclare(PrimesConfig.QUEUE, false, false, false, null);


        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");

                long deliveryTag = envelope.getDeliveryTag();
                channel.basicAck(deliveryTag, false);

                try {
                    if (pc.isPrime(Long.parseLong(message)))
                        System.out.println(message + " is PRIME");
                } finally {
                    System.out.print("#");
                }
            }
        };

        channel.basicConsume(PrimesConfig.QUEUE, false, consumer);
    }


    private class PrimeCalculator {
        public boolean isPrime(long n){
            if (n<=1)
                return false;
            else if (n <= 3)
                return true;
            else if (n%2 == 0 || n%3 == 0)
                return false;
            long i = 5;
            while (i*i <= n){
                if (n%i == 0 || n%(i+2)==0)
                    return false;
                i += 6;
            }
            return true;
        }
    }
}
