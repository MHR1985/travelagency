package travelagency;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import travelagency.repository.OfferRepository;
import travelagency.entities.Offer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

@Component
public class SendOffersAndPersist {

    private static OfferRepository repo;

    //This is how to use spring boot dependency injection in static classes!!!
    @Autowired
    public SendOffersAndPersist(OfferRepository repo) {
        this.repo = repo;
    }


    public static void sendOffer() {
        boolean run = true;
        Scanner scan = new Scanner(System.in);
        while (run) {
            System.out.println("NEW OFFER");
            System.out.println("Enter destination country:");
            String destination = scan.next().toUpperCase();
            System.out.println("Enter price:");
            Double price;
            while (!scan.hasNextDouble()) {
                System.out.println("You must enter a number");
                scan.next();
            }
            price = scan.nextDouble();


            System.out.println("Enter date (YYYY-MM-DD):");
            String date = scan.next();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            formatter.setLenient(false);
            while (true) {
                try {
                    formatter.parse(date);
                    break;
                } catch (ParseException ex) {
                    System.out.println("You must enter a valid date in the format provided: YYYY-MM-DD");
                    date = scan.next();
                }
            }


            Offer offer = new Offer(destination, price, date);
            offer = repo.save(offer);
            System.out.println("Offer id is " + offer.getId());
            Gson gson = new Gson();
            try {
                String json = gson.toJson(offer);
                System.out.println(json);
                MessageToCustomers.sendMessageToQueue(json, offer.getDestination());
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Do you want to create another offer? YES / NO");
            String choice = scan.next();
            if (choice.equalsIgnoreCase("NO")) {
                run = false;
            }
        }


    }
}
