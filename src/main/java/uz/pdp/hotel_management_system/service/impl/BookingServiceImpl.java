package uz.pdp.hotel_management_system.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.hotel_management_system.dto.BookingDto;
import uz.pdp.hotel_management_system.dto.response.Empty;
import uz.pdp.hotel_management_system.dto.response.Response;
import uz.pdp.hotel_management_system.entity.AuthUser;
import uz.pdp.hotel_management_system.entity.Booking;
import uz.pdp.hotel_management_system.entity.Room;
import uz.pdp.hotel_management_system.enums.BookingStatus;
import uz.pdp.hotel_management_system.exception.CustomException;
import uz.pdp.hotel_management_system.exception.ResourceNotFoundException;
import uz.pdp.hotel_management_system.mapper.BookingMapper;
import uz.pdp.hotel_management_system.repository.AuthUserRepository;
import uz.pdp.hotel_management_system.repository.BookingRepository;
import uz.pdp.hotel_management_system.repository.RoomRepository;
import uz.pdp.hotel_management_system.service.BookingService;
import uz.pdp.hotel_management_system.service.RoomService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {
    private final AuthUserRepository authUserRepository;
    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final BookingMapper bookingMapper;
    private final RoomService roomService;

    @Override
    public ResponseEntity<?> createBooking(BookingDto bookingDto) {
        Booking booking = bookingMapper.toEntity(bookingDto);

        AuthUser authUser = authUserRepository.findById(booking.getAuthUser().getId())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "AuthUser not found: " + booking.getAuthUser().getId()));

        Room room = roomRepository.findById(booking.getRoom().getId())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Room not found: " + booking.getRoom().getId()));

        booking.setAuthUser(authUser);
        booking.setRoom(room);
        booking.setStatus(BookingStatus.BOOKED);
        bookingRepository.saveAndFlush(booking);
        log.info("Booking successfully created");

        var response = Response.builder()
                .success(true)
                .data(bookingMapper.toDto(booking))
                .error(Empty.builder().build())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getBookingById(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Booking not found: " + bookingId));
        log.info("Booking successfully found");

        var response = Response.builder()
                .success(true)
                .data(bookingMapper.toDto(booking))
                .error(Empty.builder().build())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAllBooking(Pageable pageable) {
        List<Booking> bookings = bookingRepository.findAll();
        List<BookingDto> bookingList = bookings.stream().map(bookingMapper::toDto).toList();
        int start = pageable.getPageNumber() * pageable.getPageSize();
        int end = Math.min(start + pageable.getPageSize(), bookingList.size());
        List<BookingDto> outputBookings = bookingList.subList(start, end);
        log.info("Bookings list successfully found pageable");

        var response = Response.builder()
                .success(true)
                .data(outputBookings)
                .error(Empty.builder().build())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity<?> deleteBookingById(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Bookings npt found: " + bookingId));
        log.info("Bookings successfully deleted");
        bookingRepository.delete(booking);

        var response = Response.builder()
                .success(true)
                .error(Empty.builder().build())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> confirmBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Bookings npt found: " + bookingId));

        if (booking.getStatus() != BookingStatus.BOOKED) {
            return ResponseEntity.badRequest().body("Booking already confirmed or cancelled");
        }

        // Optional: check room availability again
        if (!roomService.isAvailable(booking.getRoom(), booking.getBeginDate(), booking.getEndDate())) {
            throw new RuntimeException("Room not available");
        }

        booking.setStatus(BookingStatus.CONFIRMED);
        bookingRepository.saveAndFlush(booking);

        var response = Response.builder()
                .success(true)
                .data(bookingMapper.toDto(booking))
                .error(Empty.builder().build())
                .build();
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> checkIn(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Bookings npt found: " + bookingId));

        if (booking.getStatus() != BookingStatus.CONFIRMED) {
            throw new RuntimeException("Cannot check-in, booking not confirmed");
        }

        booking.setStatus(BookingStatus.CHECKED_IN);
        bookingRepository.saveAndFlush(booking);

        var response = Response.builder()
                .success(true)
                .data(bookingMapper.toDto(booking))
                .error(Empty.builder().build())
                .build();
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> checkOut(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Bookings npt found: " + bookingId));

        if (booking.getStatus() != BookingStatus.CHECKED_IN) {
            throw new RuntimeException("Cannot check-out, guest not checked in yet");
        }

        booking.setStatus(BookingStatus.CHECKED_OUT);
        bookingRepository.saveAndFlush(booking);

        var response = Response.builder()
                .success(true)
                .data(bookingMapper.toDto(booking))
                .error(Empty.builder().build())
                .build();
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> getBookingByStatus(BookingStatus status) {
        List<Booking> bookings = bookingRepository.findBookingByStatus(status);

        var response = Response.builder()
                .success(true)
                .data(bookings)
                .error(Empty.builder().build())
                .build();
        return ResponseEntity.ok(response);
    }
}
