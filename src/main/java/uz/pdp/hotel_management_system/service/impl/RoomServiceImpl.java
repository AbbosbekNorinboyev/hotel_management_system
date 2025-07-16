package uz.pdp.hotel_management_system.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.hotel_management_system.dto.RoomCreateDTO;
import uz.pdp.hotel_management_system.dto.response.Response;
import uz.pdp.hotel_management_system.entity.Room;
import uz.pdp.hotel_management_system.exception.ResourceNotFoundException;
import uz.pdp.hotel_management_system.mapper.RoomMapper;
import uz.pdp.hotel_management_system.repository.RoomRepository;
import uz.pdp.hotel_management_system.service.RoomService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomServiceImpl implements RoomService {
    private final RoomMapper roomMapper;
    private final RoomRepository roomRepository;

    @Override
    public Response createRoom(RoomCreateDTO roomCreateDTO) {
        Room room = roomMapper.toEntity(roomCreateDTO);
        roomRepository.save(room);
        log.info("Room successfully created");
        return Response.builder()
                .code(HttpStatus.OK.value())
                .message("Room successfully saved")
                .success(true)
                .data(roomMapper.toDto(room))
                .build();
    }

    @Override
    public Response getRoomById(Integer roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found: " + roomId));
        log.info("Room successfully found");
        return Response.builder()
                .code(HttpStatus.OK.value())
                .message("Room successfully found")
                .success(true)
                .data(roomMapper.toDto(room))
                .build();
    }

    @Override
    public Response getAllRoom() {
        List<Room> rooms = roomRepository.findAll();
        if (!rooms.isEmpty()) {
            log.info("Room list successfully found");
            return Response.builder()
                    .code(HttpStatus.OK.value())
                    .message("Room list successfully found")
                    .success(true)
                    .data(rooms.stream().map(roomMapper::toDto).toList())
                    .build();
        }
        log.error("Room list not found");
        return Response.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message("Room list not found")
                .success(true)
                .data(rooms.stream().map(roomMapper::toDto).toList())
                .build();
    }

    @Override
    public Response updateRoom(RoomCreateDTO roomCreateDTO, Integer roomId) {
        Room roomFound = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found: " + roomId));
        roomFound.setNumber(roomCreateDTO.getNumber());
        roomFound.setNumberOfPeople(roomCreateDTO.getNumberOfPeople());
        roomFound.setPrice(roomCreateDTO.getPrice());
        roomFound.setStatus(roomCreateDTO.getStatus());
        roomFound.setState(roomCreateDTO.getState());
        roomRepository.save(roomFound);
        log.info("Room successfully updated");
        return Response.builder()
                .code(HttpStatus.OK.value())
                .message("Room successfully updated")
                .success(true)
                .build();
    }

    @Override
    public Response getAllRoomPage(Pageable pageable) {
        List<Room> rooms = roomRepository.findAll();
        if (!rooms.isEmpty()) {
            List<RoomCreateDTO> list = rooms.stream().map(roomMapper::toDto).toList();
            int start = pageable.getPageSize() * pageable.getPageNumber();
            int end = Math.min(start + pageable.getPageSize(), list.size());
            List<RoomCreateDTO> outputRooms = list.subList(start, end);
            log.info("Room list successfully found pageable");
            return Response.builder()
                    .code(HttpStatus.OK.value())
                    .message("Room list successfully found pageable")
                    .success(true)
                    .data(outputRooms)
                    .build();
        }
        return Response.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message("Room list not found pageable")
                .success(false)
                .build();
    }
}
