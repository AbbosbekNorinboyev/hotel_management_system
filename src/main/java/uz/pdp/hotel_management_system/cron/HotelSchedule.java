package uz.pdp.hotel_management_system.cron;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import uz.pdp.hotel_management_system.entity.Orders;
import uz.pdp.hotel_management_system.entity.Room;
import uz.pdp.hotel_management_system.enums.RoomState;
import uz.pdp.hotel_management_system.repository.OrdersRepository;
import uz.pdp.hotel_management_system.repository.RoomRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class HotelSchedule {
    private final OrdersRepository ordersRepository;
    private final RoomRepository roomRepository;

    // har 10 sekundda
    @Scheduled(cron = "*/10 * * * * *")
    public void checkRoomActiveOrInactive() {
        List<Orders> orders = ordersRepository.findAll();
        for (Orders order : orders) {
            // Xonani bo'shatish sharti: agar buyurtma hozirgi kunda tugagan bo'lsa
            if (order.getEndDate().isBefore(LocalDate.now()) && order.getRoom() != null) {
                Optional<Room> roomOptional = roomRepository.findById(order.getRoom().getId());
                if (roomOptional.isPresent()) {
                    Room room = roomOptional.get();
                    if (room.getState() != RoomState.EMPTY) {
                        room.setState(RoomState.EMPTY); // Xonani bo'shatish
                        roomRepository.save(room);
                    }
                }
            }
        }
    }
}
