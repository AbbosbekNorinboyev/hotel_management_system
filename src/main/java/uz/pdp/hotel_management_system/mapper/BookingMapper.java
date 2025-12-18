package uz.pdp.hotel_management_system.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import uz.pdp.hotel_management_system.dto.BookingDto;
import uz.pdp.hotel_management_system.entity.AuthUser;
import uz.pdp.hotel_management_system.entity.Booking;
import uz.pdp.hotel_management_system.entity.Room;
import uz.pdp.hotel_management_system.exception.CustomException;
import uz.pdp.hotel_management_system.repository.AuthUserRepository;
import uz.pdp.hotel_management_system.repository.RoomRepository;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BookingMapper {
    private final AuthUserRepository authUserRepository;
    private final RoomRepository roomRepository;

    public Booking toEntity(BookingDto bookingDto) {
        AuthUser authUser = authUserRepository.findById(bookingDto.getAuthUserId())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Order not found: " + bookingDto.getAuthUserId()));
        Room room = roomRepository.findById(bookingDto.getRoomId())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Room not found: " + bookingDto.getRoomId()));
        return Booking.builder()
                .id(bookingDto.getId())
                .authUser(authUser)
                .room(room)
                .numberOfPeople(bookingDto.getNumberOfPeople())
                .beginDate(bookingDto.getBeginDate())
                .endDate(bookingDto.getEndDate())
                .build();
    }

    public BookingDto toDto(Booking order) {
        AuthUser authUser = authUserRepository.findById(order.getAuthUser().getId())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Order not found: " + order.getAuthUser().getId()));
        Room room = roomRepository.findById(order.getRoom().getId())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Room not found: " + order.getRoom().getId()));
        return BookingDto.builder()
                .id(order.getId())
                .authUserId(authUser.getId())
                .roomId(room.getId())
                .numberOfPeople(order.getNumberOfPeople())
                .beginDate(order.getBeginDate())
                .endDate(order.getEndDate())
                .build();
    }

    public List<BookingDto> dtoList(List<Booking> orders) {
        if (orders != null && !orders.isEmpty()) {
            return orders.stream().map(this::toDto).toList();
        }
        return new ArrayList<>();
    }
}
