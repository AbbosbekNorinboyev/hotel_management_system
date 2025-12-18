package uz.pdp.hotel_management_system.dto;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BookingDto {
    private Long id;
    private Long authUserId;
    private Long roomId;
    private int numberOfPeople;
    private LocalDate beginDate;
    private LocalDate endDate;
}
