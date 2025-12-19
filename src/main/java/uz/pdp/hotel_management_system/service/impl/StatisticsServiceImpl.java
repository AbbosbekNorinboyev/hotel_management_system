package uz.pdp.hotel_management_system.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.hotel_management_system.dto.StatsDto;
import uz.pdp.hotel_management_system.dto.response.Empty;
import uz.pdp.hotel_management_system.dto.response.Response;
import uz.pdp.hotel_management_system.entity.Room;
import uz.pdp.hotel_management_system.repository.BookingRepository;
import uz.pdp.hotel_management_system.repository.RoomRepository;
import uz.pdp.hotel_management_system.service.StatisticsService;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {
    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;

    @Override
    public ResponseEntity<?> statistics() {
        StatsDto statsDto = new StatsDto();

        // Eng ko‘p band bo‘lgan xona
        List<Object[]> roomBookingCounts = bookingRepository.findRoomBookingAllCounts();
        if (!roomBookingCounts.isEmpty()) {
            Long roomId = (Long) roomBookingCounts.get(0)[0];
            Room room = roomRepository.findById(roomId).orElse(null);
            statsDto.setMostBookedRoom(room != null ? room.getNumber() : null);
        }

        // Bugungi daromad
        BigDecimal todayRevenue = bookingRepository.findTodayRevenue();
        statsDto.setTodayRevenue(todayRevenue);

        // Oylik booking soni
        Long monthlyBookings = bookingRepository.countMonthlyBooking();
        statsDto.setMonthlyBookings(monthlyBookings);

        var response = Response.builder()
                .success(true)
                .data(statsDto)
                .error(Empty.builder().build())
                .build();
        return ResponseEntity.ok(response);
    }
}
