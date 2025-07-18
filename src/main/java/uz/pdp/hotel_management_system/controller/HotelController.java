package uz.pdp.hotel_management_system.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.hotel_management_system.dto.HotelCreateDTO;
import uz.pdp.hotel_management_system.dto.response.Response;
import uz.pdp.hotel_management_system.service.HotelService;

@RestController
@RequestMapping("/api/hotels")
@RequiredArgsConstructor
public class HotelController {
    private final HotelService hotelService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public Response createHotel(@Valid @RequestBody HotelCreateDTO hotelCreateDTO) {
        return hotelService.createHotel(hotelCreateDTO);
    }

    @GetMapping("/{hotelId}")
    public Response getHotel(@PathVariable Integer hotelId) {
        return hotelService.getHotelById(hotelId);
    }

    @GetMapping
    public Response getAllHotel() {
        return hotelService.getAllHotel();
    }

    @PutMapping("/update/{hotelId}")
    @PreAuthorize("hasRole('ADMIN')")
    public Response updateHotel(@Valid @RequestBody HotelCreateDTO hotelCreateDTO, @PathVariable Integer hotelId) {
        return hotelService.updateHotel(hotelCreateDTO, hotelId);
    }

    @DeleteMapping("/delete/{hotelId}")
    @PreAuthorize("hasRole('ADMIN')")
    public Response deleteHotel(@PathVariable Integer hotelId) {
        return hotelService.deleteHotelById(hotelId);
    }

    @GetMapping("/page")
    public Response getAllHotelPage(@RequestParam(defaultValue = "0") Integer page,
                                    @RequestParam(defaultValue = "1") Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return hotelService.getAllHotelPage(pageable);
    }
}
