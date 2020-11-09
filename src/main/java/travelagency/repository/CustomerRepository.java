package travelagency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import travelagency.entities.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
