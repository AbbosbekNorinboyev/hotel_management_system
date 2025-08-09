package uz.pdp.hotel_management_system.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import uz.pdp.hotel_management_system.dto.PaymentDto;
import uz.pdp.hotel_management_system.entity.Payment;
import uz.pdp.hotel_management_system.entity.Room;
import uz.pdp.hotel_management_system.exception.CustomException;
import uz.pdp.hotel_management_system.repository.RoomRepository;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PaymentMapper {

    private final RoomRepository roomRepository;

    public Payment toEntity(PaymentDto paymentDto) {
        Room room = roomRepository.findById(paymentDto.getRoomId())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND,
                        "Room not found from paymentMapper: " + paymentDto.getRoomId()));
        return Payment.builder()
                .id(paymentDto.getId())
                .amount(paymentDto.getAmount())
                .createdAt(paymentDto.getCreatedAt())
                .paymentType(paymentDto.getPaymentType())
                .room(room)
                .build();
    }

    public PaymentDto toDto(Payment payment) {
        return PaymentDto.builder()
                .id(payment.getId())
                .amount(payment.getAmount())
                .createdAt(payment.getCreatedAt())
                .paymentType(payment.getPaymentType())
                .roomId(payment.getRoom().getId())
                .build();
    }

    public List<PaymentDto> dtoList(List<Payment> payments) {
        if (payments != null && !payments.isEmpty()) {
            return payments.stream().map(this::toDto).toList();
        }
        return new ArrayList<>();
    }
}
