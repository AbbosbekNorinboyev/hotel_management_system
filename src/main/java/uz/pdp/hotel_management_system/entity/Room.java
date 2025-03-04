package uz.pdp.hotel_management_system.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.hotel_management_system.enums.RoomState;

@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer number;
    private Integer numberOfPeople;
    private Double price;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
    @Enumerated(EnumType.STRING)
    private RoomState status = RoomState.ACTIVE;
    @Enumerated(EnumType.STRING)
    private RoomState state = RoomState.EMPTY;
}