package uz.pdp.hotel_management_system.service;

import org.springframework.data.domain.Pageable;
import uz.pdp.hotel_management_system.dto.PaymentCreateDTO;
import uz.pdp.hotel_management_system.dto.response.ResponseDTO;

import java.util.List;

/**
 * to'lovlar bo'yicha update va delete yozilmaydi, chunki ularni o'zgartirish ham o'chirish ham kerak emas
 */
public interface PaymentService {
    ResponseDTO<PaymentCreateDTO> createPayment(PaymentCreateDTO paymentCreateDTO);
    ResponseDTO<PaymentCreateDTO> getPayment(Integer paymentId);
    ResponseDTO<List<PaymentCreateDTO>> getAllPayment();
    ResponseDTO<List<PaymentCreateDTO>> getAllPaymentPage(Pageable pageable);
}
