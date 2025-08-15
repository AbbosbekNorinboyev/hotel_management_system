package uz.pdp.hotel_management_system;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import uz.pdp.hotel_management_system.dto.HotelDto;
import uz.pdp.hotel_management_system.dto.response.Response;
import uz.pdp.hotel_management_system.entity.Hotel;
import uz.pdp.hotel_management_system.mapper.HotelMapper;
import uz.pdp.hotel_management_system.repository.HotelRepository;
import uz.pdp.hotel_management_system.repository.RoomRepository;
import uz.pdp.hotel_management_system.service.HotelService;

import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
public class HotelServiceImplTest {
    @MockitoBean
    private HotelRepository hotelRepository; // Mock object
    @MockitoBean
    private RoomRepository roomRepository; // Mock object
    @MockitoBean
    private HotelMapper hotelMapper; // Mock object
    @Autowired
    private HotelService hotelService; // Mock dependency-lar injektsiya qilinadi

    @Test
    void testCreateHotel() {
        // Given
        Hotel hotel = Hotel.builder()
                .name("Grand Hotel")
                .address("Toshkent, Chilonzor")
                .city("Toshkent")
                .phoneNumber("+998901234567")
                .build();

        HotelDto hotelDto = HotelDto.builder()
                .name("Grand Hotel")
                .address("Toshkent, Chilonzor")
                .city("Toshkent")
                .phoneNumber("+998901234567")
                .build();

        // Mock save result
        when(hotelMapper.toEntity(hotelDto)).thenReturn(hotel);
        when(hotelMapper.toDto(hotel)).thenReturn(hotelDto);

        // When
        Response savedHotel = hotelService.createHotel(hotelDto);
        HotelDto data = (HotelDto) savedHotel.getData();

        Assertions.assertEquals(hotelDto.getName(), data.getName());

        // Verify
        Mockito.verify(hotelRepository, Mockito.times(1)).save(hotel);
        Mockito.verify(hotelMapper, Mockito.times(1)).toEntity(hotelDto);
        Mockito.verify(hotelMapper, Mockito.times(1)).toDto(hotel);
    }

    @Test
    void testGetHotelByIdSuccess() {
        // Given
        Hotel hotel = Hotel.builder()
                .id(5)
                .address("Alisher Navoiy Street")
                .city("Tashkent")
                .name("One Star")
                .phoneNumber("+998711234567")
                .build();

        HotelDto hotelDto = HotelDto.builder()
                .id(5)
                .address("Alisher Navoiy Street")
                .city("Tashkent")
                .name("One Star")
                .phoneNumber("+998711234567")
                .build();

        when(hotelRepository.findById(5)).thenReturn(Optional.of(hotel));
        when(hotelMapper.toDto(hotel)).thenReturn(hotelDto);

        // When
        Response hotelFound = hotelService.getHotelById(5);
        HotelDto data = (HotelDto) hotelFound.getData();

        // Then
        Assertions.assertEquals(hotelDto.getAddress(), data.getAddress());

        // Verify
        Mockito.verify(hotelRepository, Mockito.times(1))
                .findById(5); // faqat bi marta chaqirilishi kerak
        Mockito.verify(hotelMapper, Mockito.times(1)).toDto(hotel);
    }

    @Test
    void testUpdateHotel() {
        // Given (boshlang‘ich qiymatlar)
        Integer hotelId = 1;
        Hotel existingHotel = new Hotel(hotelId, "Old Hotel", "Old Street", "Old City", "+998900000000");
        HotelDto updatedDto = new HotelDto(hotelId, "New Hotel", "New Street", "New City", "+998911234567");
        Hotel updatedHotel = new Hotel(hotelId, "New Hotel", "New Street", "New City", "+998911234567");

        // Mock ishlatish
        Mockito.when(hotelRepository.findById(hotelId)).thenReturn(Optional.of(existingHotel));
        Mockito.when(hotelRepository.save(existingHotel)).thenReturn(updatedHotel);
        Mockito.doNothing().when(hotelMapper).update(updatedHotel, updatedDto);

        // When (method chaqiriladi)
        Response result = hotelService.updateHotel(updatedDto);

        // Then (tekshiruvlar)
        Assertions.assertNotNull(result);
        Assertions.assertEquals("Hotel successfully updated", result.getMessage());

        // Mock verify
        Mockito.verify(hotelRepository).findById(hotelId);
        Mockito.verify(hotelMapper).update(existingHotel, updatedDto);
        Mockito.verify(hotelRepository).save(existingHotel);
    }

    @Test
    void testDeleteHotel() {
        // Given
        Hotel hotel = Hotel.builder()
                .id(5)
                .address("Alisher Navoiy Street")
                .city("Tashkent")
                .name("One Star")
                .phoneNumber("+998711234567")
                .build();

        Mockito.when(hotelRepository.findById(hotel.getId())).thenReturn(Optional.of(hotel));

        Response response = hotelService.deleteHotelById(hotel.getId());

        Assertions.assertEquals("Hotel successfully deleted", response.getMessage());

        Mockito.verify(hotelRepository, Mockito.times(1)).delete(hotel);
        Mockito.verify(roomRepository, Mockito.times(1)).deleteRoomByHotelId(hotel.getId());
        Mockito.verify(hotelRepository, Mockito.times(1)).findById(hotel.getId());
    }
}
