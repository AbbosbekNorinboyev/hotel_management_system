package uz.pdp.hotel_management_system.service;

import org.springframework.data.domain.Pageable;
import uz.pdp.hotel_management_system.dto.OrderCreateDTO;
import uz.pdp.hotel_management_system.dto.response.Response;
import uz.pdp.hotel_management_system.dto.response.ResponseDTO;

import java.util.List;

public interface OrderService {
    Response createOrder(OrderCreateDTO orderCreateDTO);

    Response getOrderById(Integer orderId);

    Response getAllOrder();

    Response deleteOrderById(Integer orderId);

    Response getAllOrderPage(Pageable pageable);
}
