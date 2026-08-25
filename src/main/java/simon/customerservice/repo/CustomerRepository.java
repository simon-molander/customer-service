package simon.customerservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import simon.customerservice.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
//Optional<Customer> findByEmail(String email);