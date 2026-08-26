package simon.customerservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import simon.customerservice.customerEntity.CustomerEntity;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    Optional<CustomerEntity> findByEmail(String email);
}