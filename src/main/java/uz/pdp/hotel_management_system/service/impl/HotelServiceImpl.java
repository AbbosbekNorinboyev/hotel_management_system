package uz.pdp.hotel_management_system.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.hotel_management_system.dto.HotelDto;
import uz.pdp.hotel_management_system.dto.response.Empty;
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
                .success(true)
                .data(hotelMapper.toDto(hotel))
                .error(Empty.builder().build())
                .build();
    }

    @Override
    public Response getHotelById(Long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Hotel not found: " + hotelId));
        log.info("Hotel successfully found");
        return Response.builder()
                .success(true)
                .data(hotelMapper.toDto(hotel))
                .error(Empty.builder().build())
                .build();
    }

    @Override
    public ResponseEntity<?> getAllHotel(Pageable pageable) {
        List<Hotel> hotels = hotelRepository.findAll();
        List<HotelDto> list = hotels.stream().map(hotelMapper::toDto).toList();
        int start = pageable.getPageNumber() * pageable.getPageSize();
        int end = Math.min(start + pageable.getPageSize(), list.size());
        List<HotelDto> outputHotels = list.subList(start, end);
        log.info("Hotel list successfully found pageable");

        var response = Response.builder()
                .success(true)
                .data(outputHotels)
                .error(Empty.builder().build())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public Response updateHotel(HotelDto hotelDto) {
        Hotel hotelFound = hotelRepository.findById(hotelDto.getId())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Hotel not found: " + hotelDto.getId()));
        hotelMapper.update(hotelFound, hotelDto);
        hotelRepository.save(hotelFound);
        log.info("Hotel successfully updated");
        return Response.builder()
                .success(true)
                .data(Empty.builder().build())
                .error(Empty.builder().build())
                .build();
    }

    @Transactional
    @Override
    public Response deleteHotelById(Long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Hotel not found: " + hotelId));
        roomRepository.deleteRoomByHotelId(hotel.getId());
        hotelRepository.delete(hotel);
        log.info("Hotel successfully deleted");
        return Response.builder()
                .success(true)
                .data(Empty.builder().build())
                .error(Empty.builder().build())
                .build();
    }
}
