package uz.pdp.hotel_management_system.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Data
public class HotelDto {
    private Long id;
    @NotBlank(message = "name can not be null or empty")
    private String name;
    @NotBlank(message = "address can not be null or empty")
    private String address;
    @NotBlank(message = "city van not be null or empty")
    private String city;
    @NotBlank(message = "phoneNumber can not be null or empty")
    private String phoneNumber;
}
