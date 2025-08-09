package uz.pdp.hotel_management_system.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.hotel_management_system.dto.OrderDto;
import uz.pdp.hotel_management_system.dto.response.Response;
import uz.pdp.hotel_management_system.entity.AuthUser;
import uz.pdp.hotel_management_system.entity.Orders;
import uz.pdp.hotel_management_system.entity.Room;
import uz.pdp.hotel_management_system.exception.CustomException;
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
    public Response createOrder(OrderDto orderDto) {
        Orders order = orderMapper.toEntity(orderDto);
        AuthUser authUser = authUserRepository.findById(order.getAuthUser().getId())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "AuthUser not found: " + order.getAuthUser().getId()));
        Room room = roomRepository.findById(order.getRoom().getId())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Room not found: " + order.getRoom().getId()));
        order.setAuthUser(authUser);
        order.setRoom(room);
        ordersRepository.save(order);
        log.info("Order successfully created");
        return Response.builder()
                .code(HttpStatus.OK.value())
                .message("Order successfully created")
                .success(true)
                .data(orderMapper.toDto(order))
                .build();
    }

    @Override
    public Response getOrderById(Integer orderId) {
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Order not found: " + orderId));
        log.info("Order successfully found");
        return Response.builder()
                .code(HttpStatus.OK.value())
                .message("Order successfully found")
                .success(true)
                .data(orderMapper.toDto(order))
                .build();
    }

    @Override
    public Response getAllOrder() {
        List<Orders> orders = ordersRepository.findAll();
        if (!orders.isEmpty()) {
            log.info("Order list successfully created");
            return Response.builder()
                    .code(HttpStatus.OK.value())
                    .message("Order list successfully found")
                    .success(true)
                    .data(orderMapper.dtoList(orders))
                    .build();
        }
        return Response.builder()
                .code(HttpStatus.OK.value())
                .message("Order list not found")
                .success(false)
                .build();
    }

    @Transactional
    @Override
    public Response deleteOrderById(Integer orderId) {
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Order npt found: " + orderId));
        log.info("Order successfully deleted");
        ordersRepository.delete(order);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .message("Oder successfully deleted")
                .success(true)
                .build();
    }

    @Override
    public Response getAllOrderPage(Pageable pageable) {
        List<Orders> orders = ordersRepository.findAll();
        if (!orders.isEmpty()) {
            List<OrderDto> orderList = orders.stream().map(orderMapper::toDto).toList();
            int start = pageable.getPageNumber() * pageable.getPageSize();
            int end = Math.min(start + pageable.getPageSize(), orderList.size());
            List<OrderDto> outputOrders = orderList.subList(start, end);
            log.info("Order list successfully found pageable");
            return Response.builder()
                    .code(HttpStatus.OK.value())
                    .message("Oder list successfully found pageable")
                    .success(true)
                    .data(outputOrders)
                    .build();
        }
        return Response.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message("Oder list not found pageable")
                .success(false)
                .build();
    }
}
