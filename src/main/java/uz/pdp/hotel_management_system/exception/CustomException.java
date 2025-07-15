package uz.pdp.hotel_management_system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class CustomException extends RuntimeException {
    private final Integer code;
    private final String message;

    public CustomException(HttpStatusCode httpStatusCode, String message) {
        super(String.format("%s: %s", httpStatusCode, message));
        this.code = httpStatusCode.value();
        this.message = message;
    }
}
