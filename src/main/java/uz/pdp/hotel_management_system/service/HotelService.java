package uz.pdp.hotel_management_system.service;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import uz.pdp.hotel_management_system.dto.HotelDto;
import uz.pdp.hotel_management_system.dto.response.Response;

public interface HotelService {
    Response createHotel(HotelDto hotelDto);

    Response getHotelById(Integer hotelId);

    ResponseEntity<?> getAllHotel();

    Response updateHotel(HotelDto hotelDto);

    @Transactional
    Response deleteHotelById(Integer hotelId);

    Response getAllHotelPage(Pageable pageable);
}
