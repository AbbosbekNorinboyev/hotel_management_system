package uz.pdp.hotel_management_system.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import uz.pdp.hotel_management_system.dto.RoomDto;
import uz.pdp.hotel_management_system.entity.Hotel;
import uz.pdp.hotel_management_system.entity.Room;
import uz.pdp.hotel_management_system.exception.CustomException;
import uz.pdp.hotel_management_system.repository.HotelRepository;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RoomMapper {
    private final HotelRepository hotelRepository;

    public Room toEntity(RoomDto roomDto) {
        Hotel hotel = hotelRepository.findById(roomDto.getHotelId())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND,
                        "Hotel not found from roomMapper: " + roomDto.getHotelId()));
        return Room.builder()
                .id(roomDto.getId())
                .number(roomDto.getNumber())
                .numberOfPeople(roomDto.getNumberOfPeople())
                .price(roomDto.getPrice())
                .hotel(hotel)
                .status(roomDto.getStatus())
                .state(roomDto.getState())
                .build();
    }

    public RoomDto toDto(Room room) {
        return RoomDto.builder()
                .id(room.getId())
                .number(room.getNumber())
                .numberOfPeople(room.getNumberOfPeople())
                .price(room.getPrice())
                .hotelId(room.getHotel().getId())
                .status(room.getStatus())
                .state(room.getState())
                .build();
    }

    public List<RoomDto> dtoList(List<Room> rooms) {
        if (rooms != null && !rooms.isEmpty()) {
            return rooms.stream().map(this::toDto).toList();
        }
        return new ArrayList<>();
    }

    public void update(Room entity, RoomDto roomDto) {
        if (roomDto == null) {
            return;
        }
        if (roomDto.getNumber() != null) {
            entity.setNumber(roomDto.getNumber());
        }
        if (roomDto.getNumberOfPeople() != null) {
            entity.setNumberOfPeople(roomDto.getNumberOfPeople());
        }
        if (roomDto.getPrice() != null) {
            entity.setPrice(roomDto.getPrice());
        }
        if (roomDto.getStatus() != null) {
            entity.setStatus(roomDto.getStatus());
        }
        if (roomDto.getState() != null) {
            entity.setState(roomDto.getState());
        }
    }
}
