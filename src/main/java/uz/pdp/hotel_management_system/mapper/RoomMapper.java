package uz.pdp.hotel_management_system.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.pdp.hotel_management_system.dto.RoomDto;
import uz.pdp.hotel_management_system.entity.Room;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RoomMapper {
    public Room toEntity(RoomDto roomDto) {
        return Room.builder()
                .id(roomDto.getId())
                .number(roomDto.getNumber())
                .numberOfPeople(roomDto.getNumberOfPeople())
                .price(roomDto.getPrice())
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
                .hotelId(room.getHotel() != null ? room.getHotel().getId() : null)
                .branchId(room.getBranch() != null ? room.getBranch().getId() : null)
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
