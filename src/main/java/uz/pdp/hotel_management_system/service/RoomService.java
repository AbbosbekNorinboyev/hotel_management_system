package uz.pdp.hotel_management_system.service;

import org.springframework.data.domain.Pageable;
import uz.pdp.hotel_management_system.dto.RoomDto;
import uz.pdp.hotel_management_system.dto.response.Response;
import uz.pdp.hotel_management_system.entity.Room;

import java.time.LocalDate;

public interface RoomService {
    Response createRoom(RoomDto roomDto);

    Response getRoomById(Long roomId);

    Response getAllRoom();

    Response updateRoom(RoomDto roomDto);

    Response getAllRoomPage(Pageable pageable);

    boolean isAvailable(Room room, LocalDate beginDate, LocalDate endDate);
}
