package uz.pdp.hotel_management_system.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.hotel_management_system.dto.HotelDto;
import uz.pdp.hotel_management_system.dto.response.Response;
import uz.pdp.hotel_management_system.service.HotelService;

@RestController
@RequestMapping("/api/hotels")
@RequiredArgsConstructor
public class HotelController {
    private final HotelService hotelService;

    @PostMapping("/create")
    public Response createHotel(@Valid @RequestBody HotelDto hotelDto) {
        return hotelService.createHotel(hotelDto);
    }

    @GetMapping("/get")
    public Response getHotel(@RequestParam("hotelId") Integer hotelId) {
        return hotelService.getHotelById(hotelId);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllHotel() {
        return hotelService.getAllHotel();
    }

    @PutMapping("/update")
    public Response updateHotel(@Valid @RequestBody HotelDto hotelDto) {
        return hotelService.updateHotel(hotelDto);
    }

    @DeleteMapping("/delete")
    public Response deleteHotel(@RequestParam("hotelId") Integer hotelId) {
        return hotelService.deleteHotelById(hotelId);
    }

    @GetMapping("/getAll/page")
    public Response getAllHotelPage(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                    @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return hotelService.getAllHotelPage(pageable);
    }
}
