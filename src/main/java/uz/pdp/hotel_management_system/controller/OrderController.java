package uz.pdp.hotel_management_system.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.hotel_management_system.dto.OrderCreateDTO;
import uz.pdp.hotel_management_system.dto.response.Response;
import uz.pdp.hotel_management_system.service.OrderService;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public Response createOrder(@Valid @RequestBody OrderCreateDTO orderCreateDTO) {
        return orderService.createOrder(orderCreateDTO);
    }

    @GetMapping("/{orderId}")
    public Response getOrder(@PathVariable Integer orderId) {
        return orderService.getOrderById(orderId);
    }

    @GetMapping
    public Response getAllOrder() {
        return orderService.getAllOrder();
    }

    @DeleteMapping("/delete/{orderId}")
    @PreAuthorize("hasRole('ADMIN')")
    public Response deleteOrder(@PathVariable Integer orderId) {
        return orderService.deleteOrderById(orderId);
    }

    @GetMapping("/page")
    public Response getAllOrderPage(@RequestParam(defaultValue = "0") Integer page,
                                    @RequestParam(defaultValue = "10") Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return orderService.getAllOrderPage(pageable);
    }
}
