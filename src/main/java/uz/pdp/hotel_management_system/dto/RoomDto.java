package uz.pdp.hotel_management_system.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.hotel_management_system.enums.RoomState;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Data
public class RoomDto {
    private Long id;
    private Integer number;
    private Integer numberOfPeople;
    private Double price;
    private Long hotelId;
    @Enumerated(EnumType.STRING)
    private RoomState status = RoomState.ACTIVE;
    @Enumerated(EnumType.STRING)
    private RoomState state = RoomState.EMPTY;
}