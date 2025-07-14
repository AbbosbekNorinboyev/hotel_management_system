package uz.pdp.hotel_management_system.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.hotel_management_system.dto.PaymentCreateDTO;
import uz.pdp.hotel_management_system.dto.response.ResponseDTO;
import uz.pdp.hotel_management_system.service.PaymentService;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseDTO<PaymentCreateDTO> createPayment(@RequestBody PaymentCreateDTO paymentCreateDTO) {
        return paymentService.createPayment(paymentCreateDTO);
    }

    @GetMapping("/{paymentId}")
    public ResponseDTO<PaymentCreateDTO> getPayment(@PathVariable Integer paymentId) {
        return paymentService.getPayment(paymentId);
    }

    @GetMapping
    public ResponseDTO<List<PaymentCreateDTO>> getAllPayment() {
        return paymentService.getAllPayment();
    }

    @GetMapping("/page")
    public ResponseDTO<List<PaymentCreateDTO>> getAllPaymentPage(@RequestParam(defaultValue = "0") Integer page,
                                                                 @RequestParam(defaultValue = "10") Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return paymentService.getAllPaymentPage(pageable);
    }
}
