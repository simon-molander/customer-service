package CustomerService;

import CustomerService.Exceptions.CustomerHasActiveBookingException;
import CustomerService.Exceptions.CustomerNotFoundException;
import CustomerService.Exceptions.EmailInUseException;
import CustomerService.client.BookingClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final BookingClient bookingClient;

    public CustomerService(CustomerRepository customerRepository, BookingClient bookingClient) {
        this.customerRepository = customerRepository;
        this.bookingClient = bookingClient;
    }

    private CustomerEntity getCustomerById(long id) {
        return customerRepository.findById(id)
            .orElseThrow(
                    () -> new CustomerNotFoundException("User with id " + id + " does not exist")
            );
    }

    public CustomerResponseDTO create(CustomerCreateDTO createDTO) {
        if (customerRepository.findByEmail(createDTO.getEmail()).isPresent()) {
            throw new EmailInUseException("A customer with this email already exists");
        }

        CustomerEntity customer = new CustomerEntity();

        return createCustomerResponseDTO(createDTO, customer);
    }

    public CustomerResponseDTO update(Long id, CustomerCreateDTO createDTO) {
        CustomerEntity customer = getCustomerById(id);

        return createCustomerResponseDTO(createDTO, customer);
    }

    public void delete(Long id) {
        CustomerEntity customer = getCustomerById(id);
        boolean hasActiveBookings = bookingClient.hasActiveBookings(id);
        if (hasActiveBookings) {
            throw new CustomerHasActiveBookingException("Can not delete a customer with active bookings");
        }
        customerRepository.delete(customer);
    }

    private CustomerResponseDTO createCustomerResponseDTO(CustomerCreateDTO createDTO, CustomerEntity customer) {
        customer.setEmail(createDTO.getEmail());
        customer.setFirstName(createDTO.getFirstName());
        customer.setLastName(createDTO.getLastName());
        customer.setPhone(createDTO.getPhone());

        CustomerEntity saved = customerRepository.saveAndFlush(customer);

        return toResponse(saved);
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
        CustomerEntity customer = getCustomerById(id);

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
