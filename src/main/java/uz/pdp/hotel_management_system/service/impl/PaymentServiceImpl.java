package uz.pdp.hotel_management_system.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.hotel_management_system.dto.PaymentDto;
import uz.pdp.hotel_management_system.dto.response.Empty;
import uz.pdp.hotel_management_system.dto.response.Response;
import uz.pdp.hotel_management_system.entity.Payment;
import uz.pdp.hotel_management_system.exception.CustomException;
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
    public ResponseEntity<?> createPayment(PaymentDto paymentDto) {
        Payment payment = paymentMapper.toEntity(paymentDto);
        paymentRepository.saveAndFlush(payment);
        log.info("Payment successfully created");

        var response = Response.builder()
                .success(true)
                .data(paymentMapper.toDto(payment))
                .error(Empty.builder().build())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Payment not found: " + paymentId));
        log.info("Payment successfully found");

        var response = Response.builder()
                .success(true)
                .data(paymentMapper.toDto(payment))
                .error(Empty.builder().build())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAllPayment(Pageable pageable) {
        List<Payment> payments = paymentRepository.findAll();
        List<PaymentDto> paymentList = payments.stream().map(paymentMapper::toDto).toList();
        int start = pageable.getPageSize() * pageable.getPageNumber();
        int end = Math.min(start + pageable.getPageSize(), paymentList.size());
        List<PaymentDto> outputPayments = paymentList.subList(start, end);
        log.info("Payment list successfully found pageable");

        var response = Response.builder()
                .success(true)
                .data(outputPayments)
                .error(Empty.builder().build())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
