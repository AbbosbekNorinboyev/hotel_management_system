package uz.pdp.hotel_management_system.service;

import org.springframework.data.domain.Pageable;
import uz.pdp.hotel_management_system.dto.OrderCreateDTO;
import uz.pdp.hotel_management_system.dto.response.ResponseDTO;

import java.util.List;

public interface OrderService {
    ResponseDTO<OrderCreateDTO> createOrder(OrderCreateDTO orderCreateDTO);
    ResponseDTO<OrderCreateDTO> getOrderById(Integer orderId);
    ResponseDTO<List<OrderCreateDTO>> getAllOrder();
    ResponseDTO<Void> deleteOrderById(Integer orderId);
    ResponseDTO<List<OrderCreateDTO>> getAllOrderPage(Pageable pageable);
}
