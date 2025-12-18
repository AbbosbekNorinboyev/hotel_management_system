package uz.pdp.hotel_management_system.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.hotel_management_system.dto.BookingDto;
import uz.pdp.hotel_management_system.service.BookingService;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping("/create")
    public ResponseEntity<?> createBooking(@Valid @RequestBody BookingDto bookingDto) {
        return bookingService.createBooking(bookingDto);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getBooking(@RequestParam("bookingId") Long bookingId) {
        return bookingService.getBookingById(bookingId);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllBooking(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                           @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return bookingService.getAllBooking(pageable);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteBooking(@RequestParam("bookingId") Long bookingId) {
        return bookingService.deleteBookingById(bookingId);
    }
}
