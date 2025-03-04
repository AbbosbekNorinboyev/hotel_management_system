package uz.pdp.hotel_management_system.dto;

import lombok.*;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ErrorDTO {
    private String field;
    private String message;
}