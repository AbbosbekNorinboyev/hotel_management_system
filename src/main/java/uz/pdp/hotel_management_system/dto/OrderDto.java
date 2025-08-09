package uz.pdp.hotel_management_system.dto;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OrderDto {
    private Integer id;
    private Integer authUserId;
    private Integer roomId;
    private int numberOfPeople;
    private LocalDate beginDate;
    private LocalDate endDate;
}
