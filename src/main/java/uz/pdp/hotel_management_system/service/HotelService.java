package uz.pdp.hotel_management_system.service;

import org.springframework.data.domain.Pageable;
import uz.pdp.hotel_management_system.dto.HotelCreateDTO;
import uz.pdp.hotel_management_system.dto.response.Response;

public interface HotelService {
    Response createHotel(HotelCreateDTO hotelCreateDTO);

    Response getHotelById(Integer hotelId);

    Response getAllHotel();

    Response updateHotel(HotelCreateDTO hotelCreateDTO, Integer hotelId);

    Response deleteHotelById(Integer hotelId);

    Response getAllHotelPage(Pageable pageable);

}
