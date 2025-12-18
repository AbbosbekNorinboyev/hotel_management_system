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
                .id(5L)
                .address("Alisher Navoiy Street")
                .city("Tashkent")
                .name("One Star")
                .phoneNumber("+998711234567")
                .build();

        Room room = Room.builder()
                .id(2L)
                .number(2)
                .numberOfPeople(2)
                .price(200000.0)
                .state(RoomState.EMPTY)
                .status(RoomState.ACTIVE)
                .hotel(hotel)
                .build();

        RoomDto roomDto = RoomDto.builder()
                .id(2L)
                .number(2)
                .numberOfPeople(2)
                .price(200000.0)
                .state(RoomState.EMPTY)
                .status(RoomState.ACTIVE)
                .hotelId(hotel != null ? hotel.getId() : null)
                .build();

        Mockito.when(roomRepository.findById(2L)).thenReturn(Optional.of(room));
        Mockito.when(roomMapper.toDto(room)).thenReturn(roomDto);

        // When
        Response roomFound = roomService.getRoomById(2L);
        RoomDto data = (RoomDto) roomFound.getData();

        // Then
        Assertions.assertEquals(roomDto.getNumber(), data.getNumber());

        // Verify
        Mockito.verify(roomRepository, Mockito.times(1))
                .findById(2L); // faqat bi marta chaqirilishi kerak
        Mockito.verify(roomMapper, Mockito.times(1)).toDto(room);
    }

    @Test
    void testGetRoomByIdNotFound() {
        // Given
        Long roomId = 1000L; // DB da yo'q
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
                .id(5L)
                .address("Alisher Navoiy Street")
                .city("Tashkent")
                .name("One Star")
                .phoneNumber("+998711234567")
                .build();
        List<Room> rooms = List.of(
                new Room(2L, 2, 2, 200000.0, hotel, RoomState.EMPTY, RoomState.ACTIVE)
        );
        List<RoomDto> roomDtoList = List.of(
                new RoomDto(2L, 2, 2, 200000.0, hotel.getId(), RoomState.EMPTY, RoomState.ACTIVE)
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

    @Test
    void testUpdateRoom() {
        // Given (boshlang‘ich qiymatlar)
        Long roomId = 1L;
        Hotel hotel = new Hotel(5L, "New Hotel", "New Street", "New City", "+998911234567");
        Room existingRoom = new Room(roomId, 2, 3, 1000.0, hotel, RoomState.EMPTY, RoomState.ACTIVE);
        RoomDto updatedDto = new RoomDto(roomId, 3, 4, 10000.0, hotel.getId(), RoomState.EMPTY, RoomState.ACTIVE);
        Room updatedRoom = new Room(roomId, 3, 4, 10000.0, hotel, RoomState.EMPTY, RoomState.ACTIVE);

        // Mock ishlatish
        Mockito.when(roomRepository.findById(roomId)).thenReturn(Optional.of(existingRoom));
        Mockito.when(roomRepository.save(existingRoom)).thenReturn(updatedRoom);
        Mockito.doNothing().when(roomMapper).update(updatedRoom, updatedDto);

        // When (method chaqiriladi)
        Response result = roomService.updateRoom(updatedDto);

        // Then (tekshiruvlar)
        Assertions.assertNotNull(result);
        Assertions.assertEquals(true, result.isSuccess());

        // Mock verify
        Mockito.verify(roomRepository).findById(roomId);
        Mockito.verify(roomMapper).update(existingRoom, updatedDto);
        Mockito.verify(roomRepository).save(existingRoom);
    }
}
