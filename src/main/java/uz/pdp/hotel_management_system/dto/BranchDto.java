package uz.pdp.hotel_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Data
public class BranchDto {
    private Long id;
    private String name;
    private String code;
    private String address;
    private String city;
    private String country;
    private String phone;
    private String email;
    private Boolean active;
}
