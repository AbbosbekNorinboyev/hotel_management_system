package uz.pdp.hotel_management_system.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import uz.pdp.hotel_management_system.dto.OrderDto;
import uz.pdp.hotel_management_system.dto.response.Response;
import uz.pdp.hotel_management_system.service.OrderService;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/create")
    public Response createOrder(@Valid @RequestBody OrderDto orderDto) {
        return orderService.createOrder(orderDto);
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
    public Response deleteOrder(@PathVariable Integer orderId) {
        return orderService.deleteOrderById(orderId);
    }

    @GetMapping("/page")
    public Response getAllOrderPage(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                    @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return orderService.getAllOrderPage(pageable);
    }
}
