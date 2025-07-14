package uz.pdp.hotel_management_system.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO<T> {
    private int code;
    private String message;
    private boolean success;
    private T data;
    private List<ErrorResponse> errors;
    private HttpStatus httpStatus;
}
