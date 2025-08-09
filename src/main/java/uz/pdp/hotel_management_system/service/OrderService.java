package uz.pdp.hotel_management_system.service;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import uz.pdp.hotel_management_system.dto.OrderDto;
import uz.pdp.hotel_management_system.dto.response.Response;

public interface OrderService {
    Response createOrder(OrderDto orderDto);

    Response getOrderById(Integer orderId);

    Response getAllOrder();

    @Transactional
    Response deleteOrderById(Integer orderId);

    Response getAllOrderPage(Pageable pageable);
}
