package travelagency.entities;

import javax.persistence.*;

@Entity
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double price;
    private String date;
    private String destination;

    public Offer(){

    }
    public Offer(String destination, double price, String date) {
        this.destination = destination;
        this.price = price;
        this.date = date;
    }

    public String getDestination() {
        return destination;
    }

    public Long getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public String getDate() {
        return date;
    }
}
