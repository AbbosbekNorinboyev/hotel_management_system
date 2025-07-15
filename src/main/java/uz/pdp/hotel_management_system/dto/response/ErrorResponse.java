package uz.pdp.hotel_management_system.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"code","message"})
public class ErrorResponse {
    @JsonProperty("code")
    private Integer code;
    @JsonProperty("message")
    private String message;
}