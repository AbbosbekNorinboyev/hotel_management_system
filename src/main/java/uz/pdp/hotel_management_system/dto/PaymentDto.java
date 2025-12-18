package uz.pdp.hotel_management_system.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import uz.pdp.hotel_management_system.enums.PaymentType;

import java.time.LocalDateTime;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PaymentDto {
    private Long id;
    private Double amount;
    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;
    private Long roomId;
}
