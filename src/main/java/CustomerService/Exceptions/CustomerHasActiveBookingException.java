package CustomerService.Exceptions;

public class CustomerHasActiveBookingException extends RuntimeException {
    public CustomerHasActiveBookingException(String message) {
        super(message);
    }
}
