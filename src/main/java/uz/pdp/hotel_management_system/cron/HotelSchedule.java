package uz.pdp.hotel_management_system.cron;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import uz.pdp.hotel_management_system.entity.Booking;
import uz.pdp.hotel_management_system.entity.Room;
import uz.pdp.hotel_management_system.enums.RoomStatus;
import uz.pdp.hotel_management_system.repository.BookingRepository;
import uz.pdp.hotel_management_system.repository.RoomRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class HotelSchedule {
    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;

    // har 10 sekundda
    @Scheduled(cron = "*/10 * * * * *")
    public void checkRoomActiveOrInactive() {
        List<Booking> bookings = bookingRepository.findAll();
        for (Booking book : bookings) {
            // Xonani bo'shatish sharti: agar buyurtma hozirgi kunda tugagan bo'lsa
            if (book.getEndDate().isBefore(LocalDate.now()) && book.getRoom() != null) {
                Optional<Room> roomOptional = roomRepository.findById(book.getRoom().getId());
                if (roomOptional.isPresent()) {
                    Room room = roomOptional.get();
                    if (room.getStatus() != RoomStatus.EMPTY) {
                        room.setStatus(RoomStatus.EMPTY); // Xonani bo'shatish
                        roomRepository.save(room);
                    }
                }
            }
        }
    }
}
