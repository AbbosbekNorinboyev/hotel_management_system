package uz.pdp.hotel_management_system.service;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import uz.pdp.hotel_management_system.dto.PaymentDto;
import uz.pdp.hotel_management_system.dto.response.Response;

/**
 * to'lovlar bo'yicha update va delete yozilmaydi, chunki ularni o'zgartirish ham o'chirish ham kerak emas
 */
public interface PaymentService {
    Response createPayment(PaymentDto paymentDto);

    ResponseEntity<?> getPayment(Long paymentId);

    ResponseEntity<?> getAllPayment();

    Response getAllPaymentPage(Pageable pageable);
}
