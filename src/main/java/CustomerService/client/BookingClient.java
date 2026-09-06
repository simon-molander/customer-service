package CustomerService.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;


@Component
public class BookingClient {
    private final RestClient restClient;



    public BookingClient() {
        this.restClient = RestClient.builder()
                .baseUrl("http://localhost:8080")
                .build();
    }
    public boolean hasActiveBookings(Long customerId) {

        Boolean answer = restClient.get().uri("/api/bookings/customer/" + customerId + "/active").retrieve().body(Boolean.class);
        if (answer == null) {
            return false;
        }

        return answer;
    }
}






