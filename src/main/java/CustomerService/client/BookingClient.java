package CustomerService.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

@Component
public class BookingClient {
    private final RestClient restClient;

    public BookingClient() {
        this.restClient = RestClient.builder()
                .baseUrl("http://localhost:8080")
                .build();
    }

    public boolean hasActiveBookings(long customerId) {
        try {
            return this.restClient.get().uri("/api/bookings/customer/" + customerId + "/has-active").retrieve().body(Boolean.class);
        } catch (HttpClientErrorException.NotFound exception) {
            return false;
        }
    }
}