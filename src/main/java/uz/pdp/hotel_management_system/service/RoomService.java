package uz.pdp.hotel_management_system.service;

import org.springframework.data.domain.Pageable;
import uz.pdp.hotel_management_system.dto.RoomCreateDTO;
import uz.pdp.hotel_management_system.dto.response.Response;
import uz.pdp.hotel_management_system.dto.response.ResponseDTO;

import java.util.List;


public interface RoomService {
    Response createRoom(RoomCreateDTO roomCreateDTO);
    Response getRoomById(Integer roomId);
    Response getAllRoom();
    Response updateRoom(RoomCreateDTO roomCreateDTO, Integer roomId);
    Response getAllRoomPage(Pageable pageable);
}
