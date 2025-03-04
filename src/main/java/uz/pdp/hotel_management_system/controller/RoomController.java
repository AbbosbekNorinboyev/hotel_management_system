package uz.pdp.hotel_management_system.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.hotel_management_system.dto.ResponseDTO;
import uz.pdp.hotel_management_system.dto.RoomCreateDTO;
import uz.pdp.hotel_management_system.service.RoomService;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseDTO<RoomCreateDTO> createRoom(@RequestBody RoomCreateDTO roomCreateDTO) {
        return roomService.createRoom(roomCreateDTO);
    }

    @GetMapping("/{roomId}")
    public ResponseDTO<RoomCreateDTO> getRoom(@PathVariable Integer roomId) {
        return roomService.getRoomById(roomId);
    }

    @GetMapping
    public ResponseDTO<List<RoomCreateDTO>> getAllRoom() {
        return roomService.getAllRoom();
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseDTO<Void> updateRoom(@RequestBody RoomCreateDTO roomCreateDTO, @RequestParam Integer roomId) {
        return roomService.updateRoom(roomCreateDTO, roomId);
    }

    @GetMapping("/page")
    public ResponseDTO<List<RoomCreateDTO>> getAllRoomPage(@RequestParam(defaultValue = "0") Integer page,
                                                           @RequestParam(defaultValue = "1") Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return roomService.getAllRoomPage(pageable);
    }
}
