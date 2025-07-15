package uz.pdp.hotel_management_system.exception;

public class InvalidHeadersException extends RuntimeException {
    public InvalidHeadersException(String message) {
        super(message);
    }

    public InvalidHeadersException(String message, Throwable clause) {
        super(message, clause);
    }
}
