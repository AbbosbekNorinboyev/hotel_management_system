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
import uz.pdp.hotel_management_system.exception.CustomException;
import uz.pdp.hotel_management_system.mapper.BookingMapper;
import uz.pdp.hotel_management_system.repository.AuthUserRepository;
import uz.pdp.hotel_management_system.repository.BookingRepository;
import uz.pdp.hotel_management_system.repository.RoomRepository;
import uz.pdp.hotel_management_system.service.BookingService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {
    private final AuthUserRepository authUserRepository;
    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final BookingMapper bookingMapper;

    @Override
    public ResponseEntity<?> createBooking(BookingDto bookingDto) {
        Booking booking = bookingMapper.toEntity(bookingDto);

        AuthUser authUser = authUserRepository.findById(booking.getAuthUser().getId())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "AuthUser not found: " + booking.getAuthUser().getId()));

        Room room = roomRepository.findById(booking.getRoom().getId())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Room not found: " + booking.getRoom().getId()));

        booking.setAuthUser(authUser);
        booking.setRoom(room);
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
}
