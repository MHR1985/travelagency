package travelagency;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import travelagency.entities.Booking;
import travelagency.entities.Customer;
import travelagency.entities.Offer;
import travelagency.repository.BookingRepository;
import travelagency.repository.CustomerRepository;
import travelagency.repository.OfferRepository;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

@Component
public class CreateBookings {
    private static CustomerRepository customerRepo;
    private static BookingRepository bookingRepo;
    private static OfferRepository offerRepo;

    @Autowired
    public CreateBookings(CustomerRepository customerRepo, BookingRepository bookingRepo, OfferRepository offerRepo) {
        this.customerRepo = customerRepo;
        this.bookingRepo = bookingRepo;
        this.offerRepo = offerRepo;
    }

    public static void receive(String queue) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Channel channel;
        try (Connection connection = factory.newConnection()) {
            channel = connection.createChannel();


            channel.queueDeclare(queue, true, false, false, null);
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {

                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                try {
                    handleMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
            channel.basicConsume(queue, true, deliverCallback, consumerTag -> {
            });
        }
    }

    private static void handleMessage(String message) {
        JsonObject jobj = new Gson().fromJson(message, JsonObject.class);
        System.out.println(jobj);
        Long id = jobj.get("offerId").getAsLong();
        String name = jobj.get("name").getAsString();
        String email = jobj.get("email").getAsString();
        Customer customer = new Customer(name, email);
        customer = customerRepo.save(customer);
        Optional<Offer> offerResult = offerRepo.findById(id);
        if (offerResult.isPresent()) {
            Offer offer = offerResult.get();
            Booking booking = new Booking(customer, offer);
            booking = bookingRepo.save(booking);
            System.out.println("Booking created: " + new Gson().toJson(booking));
        } else {
            System.out.println("No offer was found with id: " + id);
        }

    }


}
