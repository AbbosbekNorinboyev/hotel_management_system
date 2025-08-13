package uz.pdp.hotel_management_system;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import uz.pdp.hotel_management_system.dto.RoomDto;
import uz.pdp.hotel_management_system.dto.response.Response;
import uz.pdp.hotel_management_system.entity.Hotel;
import uz.pdp.hotel_management_system.entity.Room;
import uz.pdp.hotel_management_system.enums.RoomState;
import uz.pdp.hotel_management_system.mapper.RoomMapper;
import uz.pdp.hotel_management_system.repository.RoomRepository;
import uz.pdp.hotel_management_system.service.impl.RoomServiceImpl;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class RoomServiceImplTest {
    @Mock
    private RoomRepository roomRepository; // Mock object
    @Mock
    private RoomMapper roomMapper; // Mock object
    @InjectMocks
    private RoomServiceImpl roomServiceImpl; // Mock dependency-lar injektsiya qilinadi

    @Test
    void testGetRoomByIdSuccess() {
        // Given
        Hotel hotel = Hotel.builder()
                .id(5)
                .address("Alisher Navoiy Street")
                .city("Tashkent")
                .name("One Star")
                .phoneNumber("+998711234567")
                .build();

        Room room = Room.builder()
                .id(2)
                .number(2)
                .numberOfPeople(2)
                .price(200000.0)
                .state(RoomState.EMPTY)
                .status(RoomState.ACTIVE)
                .hotel(hotel)
                .build();

        RoomDto roomDto = RoomDto.builder()
                .id(2)
                .number(2)
                .numberOfPeople(2)
                .price(200000.0)
                .state(RoomState.EMPTY)
                .status(RoomState.ACTIVE)
                .hotelId(hotel != null ? hotel.getId() : null)
                .build();

        Mockito.when(roomRepository.findById(2)).thenReturn(Optional.of(room));
        Mockito.when(roomMapper.toDto(room)).thenReturn(roomDto);

        // When
        Response roomFound = roomServiceImpl.getRoomById(2);
        RoomDto data = (RoomDto) roomFound.getData();

        // Then
        Assertions.assertEquals(roomDto.getNumber(), data.getNumber());
    }
}
