package simon.customerservice.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

//    @GetMapping
//    public List<CustomerResponseDTO> getAllCustomers() {
//        return customerService.findAll();
//    }
@GetMapping
   public ResponseEntity<List<CustomerResponseDTO>> getAllCustomers() {
        return ResponseEntity.ok(customerService.findAll());
}

//    @GetMapping("/{id}")
//    public CustomerResponseDTO getCustomerById(@PathVariable Long id) {
//        return customerService.findById(id);
//    }
@GetMapping("/{id}")
public ResponseEntity<CustomerResponseDTO> getCustomerById(@PathVariable Long id){
        return ResponseEntity.ok(customerService.findById(id));
}




//    @PostMapping
//    public CustomerResponseDTO createCustomer(@RequestBody CustomerCreateDTO dto) {
//        return customerService.create(dto);
//    }
    @PostMapping
    public ResponseEntity<CustomerResponseDTO> createCustomer(
            @RequestBody CustomerCreateDTO customerCreateDTO){
        CustomerResponseDTO createdCustomer = customerService.create(customerCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
    }

//    @PutMapping("/{id}")
//    public CustomerResponseDTO updateCustomer(@PathVariable Long id, @RequestBody CustomerCreateDTO dto) {
//        return customerService.update(id, dto);
//    }
@PutMapping("/{id}")
public ResponseEntity<CustomerResponseDTO> updateCustomer(
        @PathVariable Long id,
        @Valid @RequestBody CustomerCreateDTO customerCreateDTO){
        CustomerResponseDTO updatedCustomer = customerService.update(id, customerCreateDTO);
    return ResponseEntity.ok(updatedCustomer);
}




//    @DeleteMapping("/{id}")
//    public void deleteCustomer(@PathVariable Long id) {
//        customerService.delete(id);
//    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id){
        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

