package uz.pdp.hotel_management_system.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.hotel_management_system.dto.PaymentCreateDTO;
import uz.pdp.hotel_management_system.dto.response.Response;
import uz.pdp.hotel_management_system.dto.response.ResponseDTO;
import uz.pdp.hotel_management_system.entity.Payment;
import uz.pdp.hotel_management_system.exception.ResourceNotFoundException;
import uz.pdp.hotel_management_system.mapper.PaymentMapper;
import uz.pdp.hotel_management_system.repository.PaymentRepository;
import uz.pdp.hotel_management_system.service.PaymentService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {
    private final PaymentMapper paymentMapper;
    private final PaymentRepository paymentRepository;

    @Override
    public Response createPayment(PaymentCreateDTO paymentCreateDTO) {
        Payment payment = paymentMapper.toEntity(paymentCreateDTO);
        paymentRepository.save(payment);
        log.info("Payment successfully created");
        return Response.builder()
                .code(HttpStatus.OK.value())
                .message("Payment successfully created")
                .success(true)
                .data(paymentMapper.toDto(payment))
                .build();
    }

    @Override
    public Response getPayment(Integer paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found: " + paymentId));
        log.info("Payment successfully found");
        return Response.builder()
                .code(HttpStatus.OK.value())
                .message("Payment successfully found")
                .success(true)
                .data(paymentMapper.toDto(payment))
                .build();
    }

    @Override
    public Response getAllPayment() {
        List<Payment> payments = paymentRepository.findAll();
        if (!payments.isEmpty()) {
            log.info("Payment list successfully found");
            return Response.builder()
                    .code(HttpStatus.OK.value())
                    .message("Payment list successfully found")
                    .success(true)
                    .data(payments.stream().map(paymentMapper::toDto).toList())
                    .build();
        }
        log.error("Payment list not found");
        return Response.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message("Payment list not found")
                .success(true)
                .data(payments.stream().map(paymentMapper::toDto).toList())
                .build();
    }

    @Override
    public Response getAllPaymentPage(Pageable pageable) {
        List<Payment> payments = paymentRepository.findAll();
        if (!payments.isEmpty()) {
            List<PaymentCreateDTO> paymentList = payments.stream().map(paymentMapper::toDto).toList();
            int start = pageable.getPageSize() * pageable.getPageNumber();
            int end = Math.min(start + pageable.getPageSize(), paymentList.size());
            List<PaymentCreateDTO> outputPayments = paymentList.subList(start, end);
            log.info("Payment list successfully found pageable");
            return Response.builder()
                    .code(HttpStatus.OK.value())
                    .message("Payment list successfully found pageable")
                    .success(true)
                    .data(outputPayments)
                    .build();
        }
        return Response.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message("Payment list not found pageable")
                .success(false)
                .build();
    }
}
