package uz.pdp.hotel_management_system.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.hotel_management_system.dto.PaymentDto;
import uz.pdp.hotel_management_system.service.PaymentService;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity<?> createPayment(@Valid @RequestBody PaymentDto paymentDto) {
        return paymentService.createPayment(paymentDto);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getPayment(@RequestParam("paymentId") Long paymentId) {
        return paymentService.getPayment(paymentId);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllPaymentPage(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                               @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return paymentService.getAllPayment(pageable);
    }
}
