package uz.pdp.hotel_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.hotel_management_system.enums.RoomState;
import uz.pdp.hotel_management_system.enums.RoomStatus;

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
    private RoomStatus status;
    private RoomState state;
    private Long branchId;
}