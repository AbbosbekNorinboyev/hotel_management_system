package uz.pdp.hotel_management_system.service;

import org.springframework.data.domain.Pageable;
import uz.pdp.hotel_management_system.dto.response.ResponseDTO;
import uz.pdp.hotel_management_system.dto.RoomCreateDTO;

import java.util.List;


public interface RoomService {
    ResponseDTO<RoomCreateDTO> createRoom(RoomCreateDTO roomCreateDTO);
    ResponseDTO<RoomCreateDTO> getRoomById(Integer roomId);
    ResponseDTO<List<RoomCreateDTO>> getAllRoom();
    ResponseDTO<Void> updateRoom(RoomCreateDTO roomCreateDTO, Integer roomId);
    ResponseDTO<List<RoomCreateDTO>> getAllRoomPage(Pageable pageable);
}
