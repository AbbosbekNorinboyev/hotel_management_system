package uz.pdp.hotel_management_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.hotel_management_system.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Integer> {
    @Query(value = "delete from room r where r.hotel_id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    void deleteRoomByHotelId(Integer hotelId);
}