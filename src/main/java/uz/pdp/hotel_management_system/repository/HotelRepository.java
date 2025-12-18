package uz.pdp.hotel_management_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.hotel_management_system.entity.Hotel;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
}