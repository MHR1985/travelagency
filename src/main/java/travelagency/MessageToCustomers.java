package travelagency;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class MessageToCustomers {

    public final static String BINDING_KEY = "camel";
    public final static String EXCHANGE_NAME = "travelagency";


    private MessageToCustomers(){

    }

    public static void sendMessageToQueue(String message, String queue) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        // try-with-resources (closes used resources without finally)
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            // declare a direct, durable exchange
            channel.exchangeDeclare(EXCHANGE_NAME, "direct", true);

            // declare a durable, non exclusive, non auto-delete queue
            channel.queueDeclare(queue, true, false, false, null);

            // bind the queue to the exchange with the routing key 'camel'
            channel.queueBind(queue, EXCHANGE_NAME, BINDING_KEY);
            channel.basicPublish(EXCHANGE_NAME, BINDING_KEY, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
