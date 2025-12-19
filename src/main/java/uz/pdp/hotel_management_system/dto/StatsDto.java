package uz.pdp.hotel_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Data
public class StatsDto {
    private Integer mostBookedRoom;  // eng ko‘p band bo‘lgan xona
    private BigDecimal todayRevenue;   // bugungi daromad
    private Long monthlyBookings;  // oylik booking soni
}
