package uz.pdp.hotel_management_system.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"code","message"})
public class ErrorResponse implements Serializable {
    @JsonProperty("code")
    private int code;
    @JsonProperty("message")
    private String message;
}