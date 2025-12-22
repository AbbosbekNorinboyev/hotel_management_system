package uz.pdp.hotel_management_system.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.pdp.hotel_management_system.dto.PaymentDto;
import uz.pdp.hotel_management_system.entity.Payment;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PaymentMapper {
    public Payment toEntity(PaymentDto paymentDto) {
        return Payment.builder()
                .id(paymentDto.getId())
                .amount(paymentDto.getAmount())
                .createdAt(paymentDto.getCreatedAt())
                .build();
    }

    public PaymentDto toDto(Payment payment) {
        return PaymentDto.builder()
                .id(payment.getId())
                .amount(payment.getAmount())
                .createdAt(payment.getCreatedAt())
                .paymentType(payment.getPaymentType())
                .paymentStatus(payment.getPaymentStatus())
                .roomId(payment.getRoom().getId())
                .build();
    }

    public List<PaymentDto> dtoList(List<Payment> list) {
        if (list != null && !list.isEmpty()) {
            return list.stream().map(this::toDto).toList();
        }
        return new ArrayList<>();
    }
}
