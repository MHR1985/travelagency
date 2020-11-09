package travelagency.entities;

import javax.persistence.*;

@Entity
public class Booking {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Offer offer;
    @OneToOne
    private Customer customer;

    public Booking(){

    }

    public Booking(Customer customer, Offer offer) {
        this.customer = customer;
        this.offer = offer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Long getId() {
        return id;
    }

    public Offer getOffer() {
        return offer;
    }
}
