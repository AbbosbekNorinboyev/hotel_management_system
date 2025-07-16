package uz.pdp.hotel_management_system.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import uz.pdp.hotel_management_system.dto.OrderCreateDTO;
import uz.pdp.hotel_management_system.entity.AuthUser;
import uz.pdp.hotel_management_system.entity.Orders;
import uz.pdp.hotel_management_system.entity.Room;
import uz.pdp.hotel_management_system.exception.CustomException;
import uz.pdp.hotel_management_system.repository.AuthUserRepository;
import uz.pdp.hotel_management_system.repository.RoomRepository;

@Component
@RequiredArgsConstructor
public class OrderMapper {
    private final AuthUserRepository authUserRepository;
    private final RoomRepository roomRepository;

    public Orders toEntity(OrderCreateDTO orderCreateDTO) {
        AuthUser authUser = authUserRepository.findById(orderCreateDTO.getAuthUserId())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Order not found: " + orderCreateDTO.getAuthUserId()));
        Room room = roomRepository.findById(orderCreateDTO.getRoomId())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Room not found: " + orderCreateDTO.getRoomId()));
        return Orders.builder()
                .id(orderCreateDTO.getId())
                .authUser(authUser)
                .room(room)
                .numberOfPeople(orderCreateDTO.getNumberOfPeople())
                .beginDate(orderCreateDTO.getBeginDate())
                .endDate(orderCreateDTO.getEndDate())
                .build();
    }

    public OrderCreateDTO toDto(Orders order) {
        AuthUser authUser = authUserRepository.findById(order.getAuthUser().getId())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Order not found: " + order.getAuthUser().getId()));
        Room room = roomRepository.findById(order.getRoom().getId())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Room not found: " + order.getRoom().getId()));
        return OrderCreateDTO.builder()
                .id(order.getId())
                .authUserId(authUser.getId())
                .roomId(room.getId())
                .numberOfPeople(order.getNumberOfPeople())
                .beginDate(order.getBeginDate())
                .endDate(order.getEndDate())
                .build();
    }
}
