package uz.pdp.hotel_management_system.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import uz.pdp.hotel_management_system.enums.RoomState;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RoomDto {
    private Integer id;
    private Integer number;
    private Integer numberOfPeople;
    private Double price;
    private Integer hotelId;
    @Enumerated(EnumType.STRING)
    private RoomState status = RoomState.ACTIVE;
    @Enumerated(EnumType.STRING)
    private RoomState state = RoomState.EMPTY;
}