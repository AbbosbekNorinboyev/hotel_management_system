package uz.pdp.hotel_management_system.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.hotel_management_system.dto.PaymentCreateDTO;
import uz.pdp.hotel_management_system.dto.ResponseDTO;
import uz.pdp.hotel_management_system.entity.Payment;
import uz.pdp.hotel_management_system.exception.ResourceNotFoundException;
import uz.pdp.hotel_management_system.mapper.PaymentMapper;
import uz.pdp.hotel_management_system.repository.PaymentRepository;
import uz.pdp.hotel_management_system.service.PaymentService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentMapper paymentMapper;
    private final PaymentRepository paymentRepository;

    @Override
    public ResponseDTO<PaymentCreateDTO> createPayment(PaymentCreateDTO paymentCreateDTO) {
        Payment payment = paymentMapper.toEntity(paymentCreateDTO);
        paymentRepository.save(payment);
        return ResponseDTO.<PaymentCreateDTO>builder()
                .code(200)
                .message("Payment successfully created")
                .success(true)
                .data(paymentMapper.toDto(payment))
                .build();
    }

    @Override
    public ResponseDTO<PaymentCreateDTO> getPayment(Integer paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found: " + paymentId));
        return ResponseDTO.<PaymentCreateDTO>builder()
                .code(200)
                .message("Payment successfully found")
                .success(true)
                .data(paymentMapper.toDto(payment))
                .build();
    }

    @Override
    public ResponseDTO<List<PaymentCreateDTO>> getAllPayment() {
        List<Payment> payments = paymentRepository.findAll();
        return ResponseDTO.<List<PaymentCreateDTO>>builder()
                .code(200)
                .message("Payment list successfully found")
                .success(true)
                .data(payments.stream().map(paymentMapper::toDto).toList())
                .build();
    }

    @Override
    public ResponseDTO<List<PaymentCreateDTO>> getAllPaymentPage(Pageable pageable) {
        List<Payment> payments = paymentRepository.findAll();
        if (!payments.isEmpty()) {
            List<PaymentCreateDTO> paymentList = payments.stream().map(paymentMapper::toDto).toList();
            int start = pageable.getPageSize() * pageable.getPageNumber();
            int end = Math.min(start + pageable.getPageSize(), paymentList.size());
            List<PaymentCreateDTO> outputPayments = paymentList.subList(start, end);
            return ResponseDTO.<List<PaymentCreateDTO>>builder()
                    .code(200)
                    .message("Payment list successfully found")
                    .success(true)
                    .data(outputPayments)
                    .build();
        }
        return ResponseDTO.<List<PaymentCreateDTO>>builder()
                .code(404)
                .message("Payment list not found pageable")
                .success(false)
                .build();
    }
}
