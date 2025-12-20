package uz.pdp.hotel_management_system.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.hotel_management_system.dto.RoomDto;
import uz.pdp.hotel_management_system.dto.response.Empty;
import uz.pdp.hotel_management_system.dto.response.Response;
import uz.pdp.hotel_management_system.entity.Branch;
import uz.pdp.hotel_management_system.entity.Hotel;
import uz.pdp.hotel_management_system.entity.Room;
import uz.pdp.hotel_management_system.enums.BookingStatus;
import uz.pdp.hotel_management_system.exception.CustomException;
import uz.pdp.hotel_management_system.exception.ResourceNotFoundException;
import uz.pdp.hotel_management_system.mapper.RoomMapper;
import uz.pdp.hotel_management_system.repository.BookingRepository;
import uz.pdp.hotel_management_system.repository.BranchRepository;
import uz.pdp.hotel_management_system.repository.HotelRepository;
import uz.pdp.hotel_management_system.repository.RoomRepository;
import uz.pdp.hotel_management_system.service.RoomService;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomServiceImpl implements RoomService {
    private final RoomMapper roomMapper;
    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;
    private final BranchRepository branchRepository;
    private final HotelRepository hotelRepository;

    @Override
    public Response createRoom(RoomDto roomDto) {
        Hotel hotel = hotelRepository.findById(roomDto.getHotelId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Hotel not found from roomMapper: " + roomDto.getHotelId()));

        Branch branch = branchRepository.findById(roomDto.getBranchId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Branch not found: " + roomDto.getBranchId()));

        Room room = roomMapper.toEntity(roomDto);
        room.setHotel(hotel);
        room.setBranch(branch);
        roomRepository.saveAndFlush(room);
        log.info("Room successfully created");

        return Response.builder()
                .success(true)
                .data(roomMapper.toDto(room))
                .error(Empty.builder().build())
                .build();
    }

    @Override
    public Response getRoomById(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Room not found: " + roomId));
        log.info("Room successfully found");
        return Response.builder()
                .success(true)
                .data(roomMapper.toDto(room))
                .error(Empty.builder().build())
                .build();
    }

    @Override
    public Response getAllRoom() {
        List<Room> rooms = roomRepository.findAll();
        if (!rooms.isEmpty()) {
            log.info("Room list successfully found");
            return Response.builder()
                    .success(true)
                    .data(roomMapper.dtoList(rooms))
                    .error(Empty.builder().build())
                    .build();
        }
        log.error("Room list not found");
        return Response.builder()
                .success(false)
                .build();
    }

    @Override
    public Response updateRoom(RoomDto roomDto) {
        Room roomFound = roomRepository.findById(roomDto.getId())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Room not found: " + roomDto.getId()));
        roomMapper.update(roomFound, roomDto);
        roomRepository.save(roomFound);
        log.info("Room successfully updated");
        return Response.builder()
                .success(true)
                .error(Empty.builder().build())
                .build();
    }

    @Override
    public Response getAllRoomPage(Pageable pageable) {
        List<Room> rooms = roomRepository.findAll();
        if (!rooms.isEmpty()) {
            List<RoomDto> list = rooms.stream().map(roomMapper::toDto).toList();
            int start = pageable.getPageSize() * pageable.getPageNumber();
            int end = Math.min(start + pageable.getPageSize(), list.size());
            List<RoomDto> outputRooms = list.subList(start, end);
            log.info("Room list successfully found pageable");
            return Response.builder()
                    .success(true)
                    .data(outputRooms)
                    .error(Empty.builder().build())
                    .build();
        }
        return Response.builder()
                .success(false)
                .build();
    }

    @Override
    public boolean isAvailable(Room room, LocalDate beginDate, LocalDate endDate) {
        // DBdan shu xona uchun overlap qiluvchi bookinglarni tekshirish
        int count = bookingRepository.countByRoomAndStatusInAndBeginDateLessThanAndEndDateGreaterThan(
                room,
                List.of(BookingStatus.CONFIRMED, BookingStatus.CHECKED_IN),
                endDate,
                beginDate
        );
        return count == 0;
    }
}
