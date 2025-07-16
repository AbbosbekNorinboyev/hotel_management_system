package uz.pdp.hotel_management_system.service;

import org.springframework.data.domain.Pageable;
import uz.pdp.hotel_management_system.dto.PaymentCreateDTO;
import uz.pdp.hotel_management_system.dto.response.Response;

/**
 * to'lovlar bo'yicha update va delete yozilmaydi, chunki ularni o'zgartirish ham o'chirish ham kerak emas
 */
public interface PaymentService {
    Response createPayment(PaymentCreateDTO paymentCreateDTO);

    Response getPayment(Integer paymentId);

    Response getAllPayment();

    Response getAllPaymentPage(Pageable pageable);
}
