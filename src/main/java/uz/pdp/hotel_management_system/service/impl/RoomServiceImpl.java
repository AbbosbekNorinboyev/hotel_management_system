package uz.pdp.hotel_management_system.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.hotel_management_system.dto.ResponseDTO;
import uz.pdp.hotel_management_system.dto.RoomCreateDTO;
import uz.pdp.hotel_management_system.entity.Room;
import uz.pdp.hotel_management_system.exception.ResourceNotFoundException;
import uz.pdp.hotel_management_system.mapper.RoomMapper;
import uz.pdp.hotel_management_system.repository.RoomRepository;
import uz.pdp.hotel_management_system.service.RoomService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomMapper roomMapper;
    private final RoomRepository roomRepository;

    @Override
    public ResponseDTO<RoomCreateDTO> createRoom(RoomCreateDTO roomCreateDTO) {
        Room room = roomMapper.toEntity(roomCreateDTO);
        roomRepository.save(room);
        return ResponseDTO.<RoomCreateDTO>builder()
                .code(200)
                .message("Room successfully saved")
                .success(true)
                .data(roomMapper.toDto(room))
                .build();
    }

    @Override
    public ResponseDTO<RoomCreateDTO> getRoomById(Integer roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found: " + roomId));
        return ResponseDTO.<RoomCreateDTO>builder()
                .code(200)
                .message("Room successfully found")
                .success(true)
                .data(roomMapper.toDto(room))
                .build();
    }

    @Override
    public ResponseDTO<List<RoomCreateDTO>> getAllRoom() {
        List<Room> rooms = roomRepository.findAll();
        return ResponseDTO.<List<RoomCreateDTO>>builder()
                .code(200)
                .message("Room list successfully found")
                .success(true)
                .data(rooms.stream().map(roomMapper::toDto).toList())
                .build();
    }

    @Override
    public ResponseDTO<Void> updateRoom(RoomCreateDTO roomCreateDTO, Integer roomId) {
        Room roomFound = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found: " + roomId));
        roomFound.setNumber(roomCreateDTO.getNumber());
        roomFound.setNumberOfPeople(roomCreateDTO.getNumberOfPeople());
        roomFound.setPrice(roomCreateDTO.getPrice());
        roomFound.setStatus(roomCreateDTO.getStatus());
        roomFound.setState(roomCreateDTO.getState());
        roomRepository.save(roomFound);
        return ResponseDTO.<Void>builder()
                .code(200)
                .message("Room successfully updated")
                .success(true)
                .build();
    }

    @Override
    public ResponseDTO<List<RoomCreateDTO>> getAllRoomPage(Pageable pageable) {
        List<Room> rooms = roomRepository.findAll();
        if (!rooms.isEmpty()) {
            List<RoomCreateDTO> list = rooms.stream().map(roomMapper::toDto).toList();
            int start = pageable.getPageSize() * pageable.getPageNumber();
            int end = Math.min(start + pageable.getPageSize(), list.size());
            List<RoomCreateDTO> outputRooms = list.subList(start, end);
            return ResponseDTO.<List<RoomCreateDTO>>builder()
                    .code(200)
                    .message("Room list successfully found pageable")
                    .success(true)
                    .data(outputRooms)
                    .build();
        }
        return ResponseDTO.<List<RoomCreateDTO>>builder()
                .code(404)
                .message("Room list not found pageable")
                .success(false)
                .build();
    }
}
