package uz.pdp.hotel_management_system.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"success", "done", "error"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response<T, V> {
    @JsonProperty("success")
    private boolean success;
    @JsonProperty("done")
    private T done;
    @JsonProperty("error")
    private V error;
}
