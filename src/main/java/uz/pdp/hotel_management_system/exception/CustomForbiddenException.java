package uz.pdp.hotel_management_system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class CustomForbiddenException extends RuntimeException {
    public CustomForbiddenException(String message) {
        super(message);
    }
}
