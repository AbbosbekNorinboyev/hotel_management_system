package uz.pdp.hotel_management_system.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.hotel_management_system.dto.PaymentCreateDTO;
import uz.pdp.hotel_management_system.dto.response.Response;
import uz.pdp.hotel_management_system.service.PaymentService;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public Response createPayment(@Valid @RequestBody PaymentCreateDTO paymentCreateDTO) {
        return paymentService.createPayment(paymentCreateDTO);
    }

    @GetMapping("/{paymentId}")
    public Response getPayment(@PathVariable Integer paymentId) {
        return paymentService.getPayment(paymentId);
    }

    @GetMapping
    public Response getAllPayment() {
        return paymentService.getAllPayment();
    }

    @GetMapping("/page")
    public Response getAllPaymentPage(@RequestParam(defaultValue = "0") Integer page,
                                      @RequestParam(defaultValue = "10") Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return paymentService.getAllPaymentPage(pageable);
    }
}
