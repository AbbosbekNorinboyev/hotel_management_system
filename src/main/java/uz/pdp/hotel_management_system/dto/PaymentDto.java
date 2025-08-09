package uz.pdp.hotel_management_system.dto;

import jakarta.persistence.*;
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
    private Integer id;
    private Double amount;
    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;
    private Integer roomId;
}
