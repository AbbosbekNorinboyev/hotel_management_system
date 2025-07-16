package uz.pdp.hotel_management_system.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import uz.pdp.hotel_management_system.dto.PaymentCreateDTO;
import uz.pdp.hotel_management_system.entity.Payment;
import uz.pdp.hotel_management_system.entity.Room;
import uz.pdp.hotel_management_system.exception.CustomException;
import uz.pdp.hotel_management_system.repository.RoomRepository;

@Component
@RequiredArgsConstructor
public class PaymentMapper {

    private final RoomRepository roomRepository;

    public Payment toEntity(PaymentCreateDTO paymentCreateDTO) {
        Room room = roomRepository.findById(paymentCreateDTO.getRoomId())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND,
                        "Room not found from paymentMapper: " + paymentCreateDTO.getRoomId()));
        return Payment.builder()
                .id(paymentCreateDTO.getId())
                .amount(paymentCreateDTO.getAmount())
                .createdAt(paymentCreateDTO.getCreatedAt())
                .paymentType(paymentCreateDTO.getPaymentType())
                .room(room)
                .build();
    }

    public PaymentCreateDTO toDto(Payment payment) {
        return PaymentCreateDTO.builder()
                .id(payment.getId())
                .amount(payment.getAmount())
                .createdAt(payment.getCreatedAt())
                .paymentType(payment.getPaymentType())
                .roomId(payment.getRoom().getId())
                .build();
    }
}
