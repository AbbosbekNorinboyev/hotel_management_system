package uz.pdp.hotel_management_system.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import uz.pdp.hotel_management_system.dto.RoomCreateDTO;
import uz.pdp.hotel_management_system.entity.Hotel;
import uz.pdp.hotel_management_system.entity.Room;
import uz.pdp.hotel_management_system.exception.CustomException;
import uz.pdp.hotel_management_system.repository.HotelRepository;

@Component
@RequiredArgsConstructor
public class RoomMapper {

    private final HotelRepository hotelRepository;

    public Room toEntity(RoomCreateDTO roomCreateDTO) {
        Hotel hotel = hotelRepository.findById(roomCreateDTO.getHotelId())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND,
                        "Hotel not found from roomMapper: " + roomCreateDTO.getHotelId()));
        return Room.builder()
                .id(roomCreateDTO.getId())
                .number(roomCreateDTO.getNumber())
                .numberOfPeople(roomCreateDTO.getNumberOfPeople())
                .price(roomCreateDTO.getPrice())
                .hotel(hotel)
                .status(roomCreateDTO.getStatus())
                .state(roomCreateDTO.getState())
                .build();
    }

    public RoomCreateDTO toDto(Room room) {
        return RoomCreateDTO.builder()
                .id(room.getId())
                .number(room.getNumber())
                .numberOfPeople(room.getNumberOfPeople())
                .price(room.getPrice())
                .hotelId(room.getHotel().getId())
                .status(room.getStatus())
                .state(room.getState())
                .build();
    }
}
