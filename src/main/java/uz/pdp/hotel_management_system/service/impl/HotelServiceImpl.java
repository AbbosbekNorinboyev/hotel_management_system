package uz.pdp.hotel_management_system.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.hotel_management_system.dto.HotelCreateDTO;
import uz.pdp.hotel_management_system.dto.ResponseDTO;
import uz.pdp.hotel_management_system.entity.Hotel;
import uz.pdp.hotel_management_system.exception.ResourceNotFoundException;
import uz.pdp.hotel_management_system.mapper.HotelMapper;
import uz.pdp.hotel_management_system.repository.HotelRepository;
import uz.pdp.hotel_management_system.repository.RoomRepository;
import uz.pdp.hotel_management_system.service.HotelService;

import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;
    private final RoomRepository roomRepository;

    @Override
    public ResponseDTO<HotelCreateDTO> createHotel(HotelCreateDTO hotelCreateDTO) {
        Hotel hotel = hotelMapper.toEntity(hotelCreateDTO);
        hotelRepository.save(hotel);
        return ResponseDTO.<HotelCreateDTO>builder()
                .code(200)
                .message("Hotel successfully created")
                .success(true)
                .data(hotelMapper.toDto(hotel))
                .build();
    }

    @Override
    public ResponseDTO<HotelCreateDTO> getHotelById(Integer hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found: " + hotelId));
        return ResponseDTO.<HotelCreateDTO>builder()
                .code(200)
                .message("Hotel successfully found")
                .success(true)
                .data(hotelMapper.toDto(hotel))
                .build();
    }

    @Override
    public ResponseDTO<List<HotelCreateDTO>> getAllHotel() {
        List<Hotel> hotels = hotelRepository.findAll();
        return ResponseDTO.<List<HotelCreateDTO>>builder()
                .code(200)
                .message("Hotel list successfully found")
                .success(true)
                .data(hotels.stream().map(hotelMapper::toDto).toList())
                .build();
    }

    @Override
    public ResponseDTO<Void> updateHotel(HotelCreateDTO hotelCreateDTO, Integer hotelId) {
        Hotel hotelFound = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found: " + hotelId));
        Hotel hotel = hotelMapper.toEntity(hotelCreateDTO);
        hotelFound.setName(hotel.getName());
        hotelFound.setAddress(hotel.getAddress());
        hotelFound.setCity(hotel.getCity());
        hotelFound.setPhoneNumber(hotel.getPhoneNumber());
        hotelRepository.save(hotelFound);
        return ResponseDTO.<Void>builder()
                .code(200)
                .message("Hotel successfully updated")
                .success(true)
                .build();
    }

    @Override
    public ResponseDTO<Void> deleteHotelById(Integer hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found: " + hotelId));
        System.out.println("hotel = " + hotel);
        roomRepository.deleteRoomByHotelId(hotel.getId());
        hotelRepository.delete(hotel);
        return ResponseDTO.<Void>builder()
                .code(200)
                .message("Hotel successfully deleted")
                .success(true)
                .build();
    }

    @Override
    public ResponseDTO<List<HotelCreateDTO>> getAllHotelPage(Pageable pageable) {
        List<Hotel> hotels = hotelRepository.findAll();
        if (!hotels.isEmpty()) {
            List<HotelCreateDTO> list = hotels.stream().map(hotelMapper::toDto).toList();
            int start = pageable.getPageNumber() * pageable.getPageSize();
            int end = Math.min(start + pageable.getPageSize(), list.size());
            List<HotelCreateDTO> outputHotels = list.subList(start, end);
            return ResponseDTO.<List<HotelCreateDTO>>builder()
                    .code(200)
                    .message("Hotel list successfully found pageable")
                    .success(true)
                    .data(outputHotels)
                    .build();
        }
        return ResponseDTO.<List<HotelCreateDTO>>builder()
                .code(404)
                .message("Hotel list not found pageable")
                .success(false)
                .build();
    }
}
