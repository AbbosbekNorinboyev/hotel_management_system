package uz.pdp.hotel_management_system.service;

import org.springframework.data.domain.Pageable;
import uz.pdp.hotel_management_system.dto.RoomDto;
import uz.pdp.hotel_management_system.dto.response.Response;


public interface RoomService {
    Response createRoom(RoomDto roomDto);

    Response getRoomById(Integer roomId);

    Response getAllRoom();

    Response updateRoom(RoomDto roomDto);

    Response getAllRoomPage(Pageable pageable);
}
