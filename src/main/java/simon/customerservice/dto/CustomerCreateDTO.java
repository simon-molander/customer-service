package simon.customerservice.dto;

import java.lang.classfile.constantpool.StringEntry;

// data kommer in
public class CustomerCreateDTO {
    @NotBLank (message = "name is requierd")
    private String name;
    @NotBlank(massage = "Email is requierd")
    private String email;

    private String phone;

    public CustomerCreateDTO() {
    }


    public CustomerCreateDTO(String name,
                             String email,
                             String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
