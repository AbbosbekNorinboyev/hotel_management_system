package uz.pdp.hotel_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Data
public class BookingDto {
    private Long id;
    private Long authUserId;
    private Long roomId;
    private int numberOfPeople;
    private LocalDate beginDate;
    private LocalDate endDate;
}
