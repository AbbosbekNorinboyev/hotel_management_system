package uz.pdp.hotel_management_system.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.hotel_management_system.dto.OrderCreateDTO;
import uz.pdp.hotel_management_system.dto.response.ResponseDTO;
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
@Slf4j
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
        log.info("Order successfully created");
        return ResponseDTO.<OrderCreateDTO>builder()
                .code(HttpStatus.OK.value())
                .message("Order successfully created")
                .success(true)
                .data(orderMapper.toDto(order))
                .build();
    }

    @Override
    public ResponseDTO<OrderCreateDTO> getOrderById(Integer orderId) {
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + orderId));
        log.info("Order successfully found");
        return ResponseDTO.<OrderCreateDTO>builder()
                .code(HttpStatus.OK.value())
                .message("Order successfully found")
                .success(true)
                .data(orderMapper.toDto(order))
                .build();
    }

    @Override
    public ResponseDTO<List<OrderCreateDTO>> getAllOrder() {
        List<Orders> orders = ordersRepository.findAll();
        if (!orders.isEmpty()) {
            log.info("Order list successfully created");
            return ResponseDTO.<List<OrderCreateDTO>>builder()
                    .code(HttpStatus.OK.value())
                    .message("Order list successfully found")
                    .success(true)
                    .data(orders.stream().map(orderMapper::toDto).toList())
                    .build();
        }
        return ResponseDTO.<List<OrderCreateDTO>>builder()
                .code(HttpStatus.OK.value())
                .message("Order list not found")
                .success(false)
                .data(orders.stream().map(orderMapper::toDto).toList())
                .build();
    }

    @Override
    public ResponseDTO<Void> deleteOrderById(Integer orderId) {
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order npt found: " + orderId));
        log.info("Order successfully deleted");
        ordersRepository.delete(order);
        return ResponseDTO.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Oder successfully deleted")
                .success(true)
                .build();
    }

    @Override
    public ResponseDTO<List<OrderCreateDTO>> getAllOrderPage(Pageable pageable) {
        List<Orders> orders = ordersRepository.findAll();
        if (!orders.isEmpty()) {
            List<OrderCreateDTO> orderList = orders.stream().map(orderMapper::toDto).toList();
            int start = pageable.getPageNumber() * pageable.getPageSize();
            int end = Math.min(start + pageable.getPageSize(), orderList.size());
            List<OrderCreateDTO> outputOrders = orderList.subList(start, end);
            log.info("Order list successfully found pageable");
            return ResponseDTO.<List<OrderCreateDTO>>builder()
                    .code(HttpStatus.OK.value())
                    .message("Oder list successfully found pageable")
                    .success(true)
                    .data(outputOrders)
                    .build();
        }
        return ResponseDTO.<List<OrderCreateDTO>>builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message("Oder list not found pageable")
                .success(false)
                .build();
    }
}
