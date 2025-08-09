package uz.pdp.hotel_management_system.service;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import uz.pdp.hotel_management_system.dto.HotelDto;
import uz.pdp.hotel_management_system.dto.response.Response;

public interface HotelService {
    Response createHotel(HotelDto hotelDto);

    Response getHotelById(Integer hotelId);

    Response getAllHotel();

    Response updateHotel(HotelDto hotelDto, Integer hotelId);

    @Transactional
    Response deleteHotelById(Integer hotelId);

    Response getAllHotelPage(Pageable pageable);

}
