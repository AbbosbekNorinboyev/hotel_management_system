package uz.pdp.hotel_management_system.mapper;

import org.springframework.stereotype.Component;
import uz.pdp.hotel_management_system.dto.HotelDto;
import uz.pdp.hotel_management_system.entity.Hotel;

import java.util.ArrayList;
import java.util.List;

@Component
public class HotelMapper {
    public Hotel toEntity(HotelDto hotelDto) {
        return Hotel.builder()
                .id(hotelDto.getId())
                .name(hotelDto.getName())
                .address(hotelDto.getAddress())
                .city(hotelDto.getCity())
                .phoneNumber(hotelDto.getPhoneNumber())
                .build();
    }

    public HotelDto toDto(Hotel hotel) {
        return HotelDto.builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .address(hotel.getAddress())
                .city(hotel.getCity())
                .phoneNumber(hotel.getPhoneNumber())
                .build();
    }

    public List<HotelDto> dtoList(List<Hotel> hotels) {
        if (hotels != null && !hotels.isEmpty()) {
            return hotels.stream().map(this::toDto).toList();
        }
        return new ArrayList<>();
    }

    public void update(Hotel entity, HotelDto dto) {
        if (dto == null) {
            return;
        }
        if (dto.getName() != null && !dto.getName().trim().isEmpty()) {
            entity.setName(dto.getName());
        }
        if (dto.getAddress() != null && !dto.getAddress().trim().isEmpty()) {
            entity.setAddress(dto.getAddress());
        }
        if (dto.getCity() != null && !dto.getCity().trim().isEmpty()) {
            entity.setCity(dto.getCity());
        }
        if (dto.getPhoneNumber() != null && !dto.getPhoneNumber().trim().isEmpty()) {
            entity.setPhoneNumber(dto.getPhoneNumber());
        }
    }
}
