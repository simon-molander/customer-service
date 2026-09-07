package CustomerService.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

@Component
public class BookingClient {
    private final RestClient restClient;

    public BookingClient(@Value("${booking.service.url}") String apiURL) {
        this.restClient = RestClient.builder()
                .baseUrl(apiURL)
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