package uz.pdp.hotel_management_system;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import uz.pdp.hotel_management_system.dto.HotelDto;
import uz.pdp.hotel_management_system.dto.response.Response;
import uz.pdp.hotel_management_system.entity.Hotel;
import uz.pdp.hotel_management_system.mapper.HotelMapper;
import uz.pdp.hotel_management_system.repository.HotelRepository;
import uz.pdp.hotel_management_system.service.impl.HotelServiceImpl;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class HotelServiceImplTest {
    @Mock
    private HotelRepository hotelRepository; // Mock object
    @Mock
    private HotelMapper hotelMapper; // Mock object
    @InjectMocks
    private HotelServiceImpl hotelServiceImpl; // Mock dependency-lar injektsiya qilinadi

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

        Mockito.when(hotelRepository.findById(5)).thenReturn(Optional.of(hotel));
        Mockito.when(hotelMapper.toDto(hotel)).thenReturn(hotelDto);

        // When
        Response hotelFound = hotelServiceImpl.getHotelById(5);
        HotelDto data = (HotelDto) hotelFound.getData();

        // Then
        Assertions.assertEquals(hotelDto.getAddress(), data.getAddress());

        // Verify
        Mockito.verify(hotelRepository, Mockito.times(1))
                .findById(5); // faqat bi marta chaqirilishi kerak
    }
}
