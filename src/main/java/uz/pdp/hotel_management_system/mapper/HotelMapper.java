package uz.pdp.hotel_management_system.mapper;

import org.springframework.stereotype.Component;
import uz.pdp.hotel_management_system.dto.HotelCreateDTO;
import uz.pdp.hotel_management_system.entity.Hotel;

@Component
public class HotelMapper {
    public Hotel toEntity(HotelCreateDTO hotelCreateDTO) {
        return Hotel.builder()
                .id(hotelCreateDTO.getId())
                .name(hotelCreateDTO.getName())
                .address(hotelCreateDTO.getAddress())
                .city(hotelCreateDTO.getCity())
                .phoneNumber(hotelCreateDTO.getPhoneNumber())
                .build();
    }

    public HotelCreateDTO toDto(Hotel hotel) {
        return HotelCreateDTO.builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .address(hotel.getAddress())
                .city(hotel.getCity())
                .phoneNumber(hotel.getPhoneNumber())
                .build();
    }
}
