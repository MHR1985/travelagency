package travelagency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

@SpringBootApplication
public class MainTravelAgency {

    public static void main(String[] args) throws ParseException, IOException, TimeoutException {
        ConfigurableApplicationContext ctx = SpringApplication.run(MainTravelAgency.class, args);
     boolean run = true;
        while(run){
            System.out.println("Select mode: 1 for offers, 2 for bookings, 'exit' for exit");
            Scanner scan = new Scanner(System.in);
            String choice = scan.next();
            if(choice.equals("1")){
                SendOffersAndPersist.sendOffer();
            }else if(choice.equals("2")){
                CreateBookings.receive("bookings");
            }else if(choice.equals("exit")){
                run = false;
                ctx.close();
            }

        }



    }

}
