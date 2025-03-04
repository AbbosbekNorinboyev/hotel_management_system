package uz.pdp.hotel_management_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.hotel_management_system.entity.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Integer> {
}