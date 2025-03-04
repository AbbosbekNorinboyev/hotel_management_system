package uz.pdp.hotel_management_system.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.hotel_management_system.dto.OrderCreateDTO;
import uz.pdp.hotel_management_system.dto.ResponseDTO;
import uz.pdp.hotel_management_system.entity.AuthUser;
import uz.pdp.hotel_management_system.entity.Orders;
import uz.pdp.hotel_management_system.entity.Room;
import uz.pdp.hotel_management_system.exception.ResourceNotFoundException;
import uz.pdp.hotel_management_system.mapper.OrderMapper;
import uz.pdp.hotel_management_system.repository.AuthUserRepository;
import uz.pdp.hotel_management_system.repository.OrdersRepository;
import uz.pdp.hotel_management_system.repository.RoomRepository;
import uz.pdp.hotel_management_system.service.OrderService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrdersRepository ordersRepository;
    private final OrderMapper orderMapper;
    private final AuthUserRepository authUserRepository;
    private final RoomRepository roomRepository;

    @Override
    public ResponseDTO<OrderCreateDTO> createOrder(OrderCreateDTO orderCreateDTO) {
        Orders order = orderMapper.toEntity(orderCreateDTO);
        AuthUser authUser = authUserRepository.findById(order.getAuthUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("AuthUser not found: " + order.getAuthUser().getId()));
        Room room = roomRepository.findById(order.getRoom().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Room not found: " + order.getRoom().getId()));
        order.setAuthUser(authUser);
        order.setRoom(room);
        ordersRepository.save(order);
        return ResponseDTO.<OrderCreateDTO>builder()
                .code(200)
                .message("Order successfully created")
                .success(true)
                .data(orderMapper.toDto(order))
                .build();
    }

    @Override
    public ResponseDTO<OrderCreateDTO> getOrderById(Integer orderId) {
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + orderId));
        return ResponseDTO.<OrderCreateDTO>builder()
                .code(200)
                .message("Order successfully found")
                .success(true)
                .data(orderMapper.toDto(order))
                .build();
    }

    @Override
    public ResponseDTO<List<OrderCreateDTO>> getAllOrder() {
        List<Orders> orders = ordersRepository.findAll();
        return ResponseDTO.<List<OrderCreateDTO>>builder()
                .code(200)
                .message("Order list successfully found")
                .success(true)
                .data(orders.stream().map(orderMapper::toDto).toList())
                .build();
    }

    @Override
    public ResponseDTO<Void> deleteOrderById(Integer orderId) {
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order npt found: " + orderId));
        ordersRepository.delete(order);
        return ResponseDTO.<Void>builder()
                .code(200)
                .message("Oder successfully deleted")
                .success(true)
                .build();
    }

    @Override
    public ResponseDTO<List<OrderCreateDTO>> getAllOrderPage(Pageable pageable) {
        List<Orders> orders = ordersRepository.findAll();
        System.out.println("orders = " + orders);
        if (!orders.isEmpty()) {
            List<OrderCreateDTO> orderList = orders.stream().map(orderMapper::toDto).toList();
            int start = pageable.getPageNumber() * pageable.getPageSize();
            int end = Math.min(start + pageable.getPageSize(), orderList.size());
            List<OrderCreateDTO> outputOrders = orderList.subList(start, end);
            System.out.println("outputOrders = " + outputOrders);
            return ResponseDTO.<List<OrderCreateDTO>>builder()
                    .code(200)
                    .message("Oder list successfully found")
                    .success(true)
                    .data(outputOrders)
                    .build();
        }
        return ResponseDTO.<List<OrderCreateDTO>>builder()
                .code(404)
                .message("Oder list not found pageable")
                .success(false)
                .build();
    }
}
