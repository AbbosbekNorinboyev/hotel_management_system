package uz.pdp.hotel_management_system.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO<T> {
    private int code;
    private String message;
    private boolean success;
    private T data;
    private List<ErrorDTO> errors;
    private HttpStatus httpStatus;
}
