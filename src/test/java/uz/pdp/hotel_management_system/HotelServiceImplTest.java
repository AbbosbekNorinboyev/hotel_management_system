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

import java.util.List;
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

    @Test
    void testGetHotelByIdNotFound() {
        // Given
        Integer hotelId = 1000;
        Mockito.when(hotelRepository.findById(hotelId)).thenReturn(Optional.empty());

        // When
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class,
                () -> hotelService.getHotelById(hotelId));

        // Then
        Assertions.assertEquals("Hotel not found: " + hotelId, exception.getMessage());

        // Verify
        Mockito.verify(hotelRepository, Mockito.times(1)).findById(hotelId);
    }

    @Test
    void testGetAllHotel() {
        // Given
        List<Hotel> hotels = List.of(
                new Hotel(5, "One Star", "Alisher Navoiy Street", "Tashkent", "+998711234567"),
                new Hotel(6, "Two Star", "Amir Temur Street", "Tashkent", "+998721234567"),
                new Hotel(7, "1", "1", "1", "1"),
                new Hotel(8, "1", "1", "1", "1"),
                new Hotel(9, "1", "1", "1", "1"),
                new Hotel(10, "1", "1", "1", "1"),
                new Hotel(11, "1", "1", "1", "1"),
                new Hotel(12, "string", "string", "string", "string"),
                new Hotel(13, "string", "string", "string", "string"),
                new Hotel(14, "string", "string", "string", "string"),
                new Hotel(15, "string", "string", "string", "string"),
                new Hotel(16, "string", "string", "string", "string")
        );

        List<HotelDto> hotelDtoList = List.of(
                new HotelDto(5, "One Star", "Alisher Navoiy Street", "Tashkent", "+998711234567"),
                new HotelDto(6, "Two Star", "Amir Temur Street", "Tashkent", "+998721234567"),
                new HotelDto(7, "1", "1", "1", "1"),
                new HotelDto(8, "1", "1", "1", "1"),
                new HotelDto(9, "1", "1", "1", "1"),
                new HotelDto(10, "1", "1", "1", "1"),
                new HotelDto(11, "1", "1", "1", "1"),
                new HotelDto(12, "string", "string", "string", "string"),
                new HotelDto(13, "string", "string", "string", "string"),
                new HotelDto(14, "string", "string", "string", "string"),
                new HotelDto(15, "string", "string", "string", "string"),
                new HotelDto(16, "string", "string", "string", "string")
        );
        Mockito.when(hotelRepository.findAll()).thenReturn(hotels);
        Mockito.when(hotelMapper.dtoList(hotels)).thenReturn(hotelDtoList);

        // When
        Response response = hotelService.getAllHotel();

        List<HotelDto> data = (List<HotelDto>) response.getData();
        Assertions.assertEquals(data.size(), 12);

        // Verify that repository method was called once
        Mockito.verify(hotelRepository, Mockito.times(1)).findAll();
        Mockito.verify(hotelMapper, Mockito.times(1)).dtoList(hotels);
    }
}
