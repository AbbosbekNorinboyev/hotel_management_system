package uz.pdp.hotel_management_system.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import uz.pdp.hotel_management_system.dto.RoomDto;
import uz.pdp.hotel_management_system.dto.response.Response;
import uz.pdp.hotel_management_system.service.RoomService;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @PostMapping("/create")
    public Response createRoom(@Valid @RequestBody RoomDto roomDto) {
        return roomService.createRoom(roomDto);
    }

    @GetMapping("/get")
    public Response getRoom(@RequestParam("roomId") Integer roomId) {
        return roomService.getRoomById(roomId);
    }

    @GetMapping("/getAll")
    public Response getAllRoom() {
        return roomService.getAllRoom();
    }

    @PutMapping("/update")
    public Response updateRoom(@Valid @RequestBody RoomDto roomDto) {
        return roomService.updateRoom(roomDto);
    }

    @GetMapping("/getAll/page")
    public Response getAllRoomPage(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                   @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return roomService.getAllRoomPage(pageable);
    }
}
