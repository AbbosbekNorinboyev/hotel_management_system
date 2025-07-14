package uz.pdp.hotel_management_system.service;

import uz.pdp.hotel_management_system.dto.HotelCreateDTO;
import uz.pdp.hotel_management_system.dto.response.ResponseDTO;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HotelService {
    ResponseDTO<HotelCreateDTO> createHotel(HotelCreateDTO hotelCreateDTO);

    ResponseDTO<HotelCreateDTO> getHotelById(Integer hotelId);

    ResponseDTO<List<HotelCreateDTO>> getAllHotel();

    ResponseDTO<Void> updateHotel(HotelCreateDTO hotelCreateDTO, Integer hotelId);

    ResponseDTO<Void> deleteHotelById(Integer hotelId);

    ResponseDTO<List<HotelCreateDTO>> getAllHotelPage(Pageable pageable);

}
