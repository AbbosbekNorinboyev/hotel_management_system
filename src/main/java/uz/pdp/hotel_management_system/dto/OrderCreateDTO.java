package uz.pdp.hotel_management_system.dto;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.hotel_management_system.entity.AuthUser;
import uz.pdp.hotel_management_system.entity.Room;

import java.time.LocalDate;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OrderCreateDTO {
    private Integer id;
    private Integer authUserId;
    private Integer roomId;
    private int numberOfPeople;
    private LocalDate beginDate;
    private LocalDate endDate;
}
