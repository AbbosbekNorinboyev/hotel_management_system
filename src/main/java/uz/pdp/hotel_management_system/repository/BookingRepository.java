package uz.pdp.hotel_management_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.pdp.hotel_management_system.entity.Booking;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("select b.room.id, count(b.id) as room_count" +
            " from Booking b " +
            " group by b.room.id " +
            " order by room_count desc")
    List<Object[]> findRoomBookingCounts();

    @Query("SELECT SUM(b.room.price) FROM Booking b " +
            "WHERE b.beginDate = CURRENT_DATE")
    BigDecimal findTodayRevenue();

    @Query("select count(b.id) from Booking b" +
            " where month(b.beginDate) = month(current_date)" +
            " and year(b.endDate) = year(current_date )")
    Long countMonthlyBooking();
}