package uz.pdp.hotel_management_system.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import uz.pdp.hotel_management_system.dto.OrderDto;
import uz.pdp.hotel_management_system.entity.AuthUser;
import uz.pdp.hotel_management_system.entity.Orders;
import uz.pdp.hotel_management_system.entity.Room;
import uz.pdp.hotel_management_system.exception.CustomException;
import uz.pdp.hotel_management_system.repository.AuthUserRepository;
import uz.pdp.hotel_management_system.repository.RoomRepository;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderMapper {
    private final AuthUserRepository authUserRepository;
    private final RoomRepository roomRepository;

    public Orders toEntity(OrderDto orderDto) {
        AuthUser authUser = authUserRepository.findById(orderDto.getAuthUserId())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Order not found: " + orderDto.getAuthUserId()));
        Room room = roomRepository.findById(orderDto.getRoomId())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Room not found: " + orderDto.getRoomId()));
        return Orders.builder()
                .id(orderDto.getId())
                .authUser(authUser)
                .room(room)
                .numberOfPeople(orderDto.getNumberOfPeople())
                .beginDate(orderDto.getBeginDate())
                .endDate(orderDto.getEndDate())
                .build();
    }

    public OrderDto toDto(Orders order) {
        AuthUser authUser = authUserRepository.findById(order.getAuthUser().getId())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Order not found: " + order.getAuthUser().getId()));
        Room room = roomRepository.findById(order.getRoom().getId())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Room not found: " + order.getRoom().getId()));
        return OrderDto.builder()
                .id(order.getId())
                .authUserId(authUser.getId())
                .roomId(room.getId())
                .numberOfPeople(order.getNumberOfPeople())
                .beginDate(order.getBeginDate())
                .endDate(order.getEndDate())
                .build();
    }

    public List<OrderDto> dtoList(List<Orders> orders) {
        if (orders != null && !orders.isEmpty()) {
            return orders.stream().map(this::toDto).toList();
        }
        return new ArrayList<>();
    }
}
