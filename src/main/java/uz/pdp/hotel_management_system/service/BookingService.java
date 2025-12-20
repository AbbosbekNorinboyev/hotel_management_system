package uz.pdp.hotel_management_system.service;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import uz.pdp.hotel_management_system.dto.BookingDto;

public interface BookingService {
    ResponseEntity<?> createBooking(BookingDto bookingDto);

    ResponseEntity<?> getBookingById(Long bookingId);

    ResponseEntity<?> getAllBooking(Pageable pageable);

    @Transactional
    ResponseEntity<?> deleteBookingById(Long bookingId);

    ResponseEntity<?> confirmBooking(Long bookingId);

    ResponseEntity<?> checkIn(Long bookingId);

    ResponseEntity<?> checkOut(Long bookingId);
}
