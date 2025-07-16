package uz.pdp.hotel_management_system.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uz.pdp.hotel_management_system.dto.response.Empty;
import uz.pdp.hotel_management_system.dto.response.ErrorResponse;
import uz.pdp.hotel_management_system.dto.response.Response;
import uz.pdp.hotel_management_system.dto.response.ResponseDTO;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandle {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> exception(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(fieldError -> fieldError.getField() + " " + fieldError.getDefaultMessage()).toList();
        StringBuilder sb = new StringBuilder();
        errors.forEach(s -> sb.append(s).append(System.lineSeparator()));
        String errorMessage = !sb.toString().isEmpty() ? sb.toString() : ex.getMessage();

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(errorMessage)
                .build();

        Response responseData = Response.builder()
                .success(false)
                .error(errorResponse)
                .data(Empty.builder().build())
                .build();
        return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseDTO<Void> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException) {
        return ResponseDTO.<Void>builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message(resourceNotFoundException.getMessage())
                .success(false)
                .build();
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleGeneralCustomExceptions(CustomException ex) {
        log.error("ErrorStatus: {}, Message: {} ", ex.getCode(), ex.getMessage());
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(ex.getCode())
                .message("Something wrong -> " + ex.getMessage())
                .build();

        var response = Response.builder()
                .success(false)
                .error(errorResponse)
                .data(Empty.builder().build())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO<Void>> handleException(Exception exception) {
        ResponseDTO<Void> responseDTO = ResponseDTO.<Void>builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Something wrong -> " + exception.getMessage())
                .success(false)
                .build();
        return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CustomForbiddenException.class)
    public ResponseDTO<Void> handleForbidden(CustomForbiddenException customForbiddenException) {
        return ResponseDTO.<Void>builder()
                .code(HttpStatus.FORBIDDEN.value())
                .message("Access denied -> " + customForbiddenException.getMessage())
                .httpStatus(HttpStatus.FORBIDDEN)
                .success(false)
                .build();
    }
}
