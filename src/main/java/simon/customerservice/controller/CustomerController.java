package simon.customerservice.controller;

import org.springframework.web.bind.annotation.*;
import simon.customerservice.dto.CustomerCreateDTO;
import simon.customerservice.dto.CustomerResponseDTO;
import simon.customerservice.service.CustomerService;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<CustomerResponseDTO> getAllCustomers() {
        return customerService.findAll();
    }


    @GetMapping("/{id}")
    public CustomerResponseDTO getCustomerById(@PathVariable Long id) {
        return customerService.findById(id);
    }

    @PostMapping
    public CustomerResponseDTO createCustomer(@RequestBody CustomerCreateDTO dto) {
        return customerService.create(dto);
    }

    @PutMapping("/{id}")
    public CustomerResponseDTO updateCustomer(@PathVariable Long id, @RequestBody CustomerCreateDTO dto) {
        return customerService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerService.delete(id);
    }
}

