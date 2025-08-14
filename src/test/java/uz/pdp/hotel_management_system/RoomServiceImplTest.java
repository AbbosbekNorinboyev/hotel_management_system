package uz.pdp.hotel_management_system;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import uz.pdp.hotel_management_system.dto.RoomDto;
import uz.pdp.hotel_management_system.dto.response.Response;
import uz.pdp.hotel_management_system.entity.Hotel;
import uz.pdp.hotel_management_system.entity.Room;
import uz.pdp.hotel_management_system.enums.RoomState;
import uz.pdp.hotel_management_system.mapper.RoomMapper;
import uz.pdp.hotel_management_system.repository.RoomRepository;
import uz.pdp.hotel_management_system.service.RoomService;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class RoomServiceImplTest {
    @MockitoBean
    private RoomRepository roomRepository; // Mock object
    @MockitoBean
    private RoomMapper roomMapper; // Mock object
    @Autowired
    private RoomService roomService; // Mock dependency-lar injektsiya qilinadi

    @Test
    void testCreateRoom() {
        Hotel hotel = Hotel.builder()
                .name("Grand Hotel")
                .address("Toshkent, Chilonzor")
                .city("Toshkent")
                .phoneNumber("+998901234567")
                .build();

        Room room = Room.builder()
                .number(3)
                .numberOfPeople(3)
                .price(300000.0)
                .state(RoomState.EMPTY)
                .status(RoomState.ACTIVE)
                .hotel(hotel)
                .build();

        RoomDto roomDto = RoomDto.builder()
                .number(3)
                .numberOfPeople(3)
                .price(300000.0)
                .state(RoomState.EMPTY)
                .status(RoomState.ACTIVE)
                .hotelId(hotel != null ? hotel.getId() : null)
                .build();

        Mockito.when(roomMapper.toEntity(roomDto)).thenReturn(room);
        Mockito.when(roomMapper.toDto(room)).thenReturn(roomDto);

        // When
        Response savedRoom = roomService.createRoom(roomDto);
        Room data = (Room) savedRoom.getData();

        Assertions.assertEquals(roomDto.getNumber(), data.getNumber());

        // Verify
        Mockito.verify(roomRepository, Mockito.times(1)).save(room);
        Mockito.verify(roomMapper, Mockito.times(1)).toEntity(roomDto);
        Mockito.verify(roomMapper, Mockito.times(1)).toDto(room);
    }

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
        Response roomFound = roomService.getRoomById(2);
        RoomDto data = (RoomDto) roomFound.getData();

        // Then
        Assertions.assertEquals(roomDto.getNumber(), data.getNumber());

        // Verify
        Mockito.verify(roomRepository, Mockito.times(1))
                .findById(2); // faqat bi marta chaqirilishi kerak
        Mockito.verify(roomMapper, Mockito.times(1)).toDto(room);
    }

    @Test
    void testGetRoomByIdNotFound() {
        // Given
        Integer roomId = 1000; // DB da yo'q
        Mockito.when(roomRepository.findById(roomId)).thenReturn(Optional.empty());

        // When
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class,
                () -> roomService.getRoomById(roomId));

        // Then
        Assertions.assertEquals("Room not found: " + roomId, exception.getMessage());

        // Verify
        Mockito.verify(roomRepository, Mockito.times(1)).findById(roomId);
    }

    @Test
    void testGetAllRoom() {
        // Given
        Hotel hotel = Hotel.builder()
                .id(5)
                .address("Alisher Navoiy Street")
                .city("Tashkent")
                .name("One Star")
                .phoneNumber("+998711234567")
                .build();
        List<Room> rooms = List.of(
                new Room(2, 2, 2, 200000.0, hotel, RoomState.EMPTY, RoomState.ACTIVE)
        );
        List<RoomDto> roomDtoList = List.of(
                new RoomDto(2, 2, 2, 200000.0, hotel.getId(), RoomState.EMPTY, RoomState.ACTIVE)
        );
        Mockito.when(roomRepository.findAll()).thenReturn(rooms);
        Mockito.when(roomMapper.dtoList(rooms)).thenReturn(roomDtoList);

        // When
        Response response = roomService.getAllRoom();

        List<RoomDto> data = (List<RoomDto>) response.getData();
        Assertions.assertEquals(data.size(), 1);

        // Verify that repository method was called once
        Mockito.verify(roomRepository, Mockito.times(1)).findAll();
        Mockito.verify(roomMapper, Mockito.times(1)).dtoList(rooms);
    }
}
