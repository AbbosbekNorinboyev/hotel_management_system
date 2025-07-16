package uz.pdp.hotel_management_system.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.hotel_management_system.dto.RoomCreateDTO;
import uz.pdp.hotel_management_system.dto.response.Response;
import uz.pdp.hotel_management_system.service.RoomService;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public Response createRoom(@Valid @RequestBody RoomCreateDTO roomCreateDTO) {
        System.out.println("roomCreateDTO = " + roomCreateDTO);
        return roomService.createRoom(roomCreateDTO);
    }

    @GetMapping("/{roomId}")
    public Response getRoom(@PathVariable Integer roomId) {
        return roomService.getRoomById(roomId);
    }

    @GetMapping
    public Response getAllRoom() {
        return roomService.getAllRoom();
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public Response updateRoom(@Valid @RequestBody RoomCreateDTO roomCreateDTO, @RequestParam Integer roomId) {
        return roomService.updateRoom(roomCreateDTO, roomId);
    }

    @GetMapping("/page")
    public Response getAllRoomPage(@RequestParam(defaultValue = "0") Integer page,
                                   @RequestParam(defaultValue = "1") Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return roomService.getAllRoomPage(pageable);
    }
}
