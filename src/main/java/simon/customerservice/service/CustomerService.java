package simon.customerservice.service;


import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import simon.customerservice.customerEntity.CustomerEntity;
import simon.customerservice.dto.CustomerCreateDTO;
import simon.customerservice.dto.CustomerResponseDTO;
import simon.customerservice.exceptions.CustomerException;
import simon.customerservice.repo.CustomerRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerResponseDTO create(CustomerCreateDTO createDTO) {
        if (customerRepository.findByEmail(createDTO.getEmail()).isPresent()) {
            throw new CustomerException("Email already in use");
        }

        CustomerEntity customer = new CustomerEntity();

        return createCustomerResponseDTO(createDTO, customer);
    }

    public CustomerResponseDTO update(Long id, CustomerCreateDTO createDTO) {
        CustomerEntity customer = customerRepository.findById(id)
                .orElseThrow(
                        () -> new CustomerException("User with id " + String.valueOf(id) + " does not exist")
                );

        return createCustomerResponseDTO(createDTO, customer);
    }

    @Transactional
    public void delete(Long id) {
        CustomerEntity customer = customerRepository.findById(id)
                .orElseThrow(
                        () -> new CustomerException("User with id " + String.valueOf(id) + " does not exist")
                );

        customerRepository.delete(customer);
    }

    private CustomerResponseDTO createCustomerResponseDTO(CustomerCreateDTO createDTO, CustomerEntity customer) {
        customer.setEmail(createDTO.getEmail());
        customer.setFirstName(createDTO.getFirstName());
        customer.setLastName(createDTO.getLastName());
        customer.setPhone(createDTO.getPhone());

        CustomerEntity saved = customerRepository.saveAndFlush(customer);

        CustomerEntity reloaded = customerRepository.findById(saved.getId())
                .orElseThrow(() -> new CustomerException("Customer not found after save"));
        return toResponse(reloaded);
    }

    public CustomerResponseDTO toResponse(CustomerEntity customer) {
        CustomerResponseDTO response = new CustomerResponseDTO();
        response.setId(customer.getId());
        response.setEmail(customer.getEmail());
        response.setFirstName(customer.getFirstName());
        response.setLastName(customer.getLastName());
        response.setPhone(customer.getPhone());

        return response;
    }

    public CustomerResponseDTO findById(Long id) {
        CustomerEntity customer = customerRepository.findById(id)
                .orElseThrow(
                        () -> new CustomerException("User with id " + String.valueOf(id) + " does not exist")
                );

        return toResponse(customer);
    }

    public List<CustomerResponseDTO> findAll() {
        List<CustomerEntity> customers = customerRepository.findAll();

        List<CustomerResponseDTO> responseBookings = new ArrayList<>();
        for (CustomerEntity customer : customers) {
            responseBookings.add(toResponse(customer));
        }

        return responseBookings;
    }
}
