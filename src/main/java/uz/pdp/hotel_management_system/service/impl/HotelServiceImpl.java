package uz.pdp.hotel_management_system.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.hotel_management_system.dto.HotelDto;
import uz.pdp.hotel_management_system.dto.response.Response;
import uz.pdp.hotel_management_system.entity.Hotel;
import uz.pdp.hotel_management_system.exception.CustomException;
import uz.pdp.hotel_management_system.mapper.HotelMapper;
import uz.pdp.hotel_management_system.repository.HotelRepository;
import uz.pdp.hotel_management_system.repository.RoomRepository;
import uz.pdp.hotel_management_system.service.HotelService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HotelServiceImpl implements HotelService {
    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;
    private final RoomRepository roomRepository;

    @Override
    public Response createHotel(HotelDto hotelDto) {
        Hotel hotel = hotelMapper.toEntity(hotelDto);
        hotelRepository.save(hotel);
        log.info("Hotel successfully created");
        return Response.builder()
                .code(HttpStatus.OK.value())
                .message("Hotel successfully created")
                .success(true)
                .data(hotelMapper.toDto(hotel))
                .build();
    }

    @Override
    public Response getHotelById(Integer hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Hotel not found: " + hotelId));
        log.info("Hotel successfully found");
        return Response.builder()
                .code(HttpStatus.OK.value())
                .message("Hotel successfully found")
                .success(true)
                .data(hotelMapper.toDto(hotel))
                .build();
    }

    @Override
    public Response getAllHotel() {
        List<Hotel> hotels = hotelRepository.findAll();
        if (!hotels.isEmpty()) {
            log.info("Hotel list successfully found");
            return Response.builder()
                    .code(HttpStatus.OK.value())
                    .message("Hotel list successfully found")
                    .success(true)
                    .data(hotelMapper.dtoList(hotels))
                    .build();
        }
        log.error("Hotel list not found");
        return Response.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message("Hotel list not found")
                .success(false)
                .build();
    }

    @Override
    public Response updateHotel(HotelDto hotelDto, Integer hotelId) {
        Hotel hotelFound = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Hotel not found: " + hotelId));
        Hotel hotel = hotelMapper.toEntity(hotelDto);
        hotelFound.setName(hotel.getName());
        hotelFound.setAddress(hotel.getAddress());
        hotelFound.setCity(hotel.getCity());
        hotelFound.setPhoneNumber(hotel.getPhoneNumber());
        hotelRepository.save(hotelFound);
        log.info("Hotel successfully updated");
        return Response.builder()
                .code(HttpStatus.OK.value())
                .message("Hotel successfully updated")
                .success(true)
                .build();
    }

    @Transactional
    @Override
    public Response deleteHotelById(Integer hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Hotel not found: " + hotelId));
        System.out.println("hotel = " + hotel);
        roomRepository.deleteRoomByHotelId(hotel.getId());
        hotelRepository.delete(hotel);
        log.info("Hotel successfully updated");
        return Response.builder()
                .code(HttpStatus.OK.value())
                .message("Hotel successfully deleted")
                .success(true)
                .build();
    }

    @Override
    public Response getAllHotelPage(Pageable pageable) {
        List<Hotel> hotels = hotelRepository.findAll();
        if (!hotels.isEmpty()) {
            List<HotelDto> list = hotels.stream().map(hotelMapper::toDto).toList();
            int start = pageable.getPageNumber() * pageable.getPageSize();
            int end = Math.min(start + pageable.getPageSize(), list.size());
            List<HotelDto> outputHotels = list.subList(start, end);
            log.info("Hotel list successfully found pageable");
            return Response.builder()
                    .code(HttpStatus.OK.value())
                    .message("Hotel list successfully found pageable")
                    .success(true)
                    .data(outputHotels)
                    .build();
        }
        return Response.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message("Hotel list not found pageable")
                .success(false)
                .build();
    }
}
