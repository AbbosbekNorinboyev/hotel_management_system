package uz.pdp.hotel_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.hotel_management_system.enums.PaymentStatus;
import uz.pdp.hotel_management_system.enums.PaymentType;

import java.time.LocalDateTime;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Data
public class PaymentDto {
    private Long id;
    private Double amount;
    private LocalDateTime createdAt;
    private PaymentType paymentType;
    private PaymentStatus paymentStatus;
    private Long roomId;
}
