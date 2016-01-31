package co.johnrowley.distributed;

/**
 * Created by RowleyJohn on 1/30/2016.
 */
public class RabbitConfig {
    public static final String URL = System.getenv("RABBIT_URL");
    public static final String USERNAME = System.getenv("RABBIT_USERNAME");
    public static final String PASSWORD = System.getenv("RABBIT_PASSWORD");
}
