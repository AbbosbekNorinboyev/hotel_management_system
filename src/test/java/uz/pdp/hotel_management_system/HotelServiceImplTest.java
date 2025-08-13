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
import uz.pdp.hotel_management_system.service.HotelService;

import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
public class HotelServiceImplTest {
    @MockitoBean
    private HotelRepository hotelRepository; // Mock object
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
}
