package uz.pdp.hotel_management_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.hotel_management_system.enums.RoomState;
import uz.pdp.hotel_management_system.enums.RoomStatus;

@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Data
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "room_id_seq")
    @SequenceGenerator(name = "room_id_seq", sequenceName = "room_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false, unique = true)
    private Integer number;

    private Integer numberOfPeople;
    private Double price;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @Enumerated(EnumType.STRING)
    private RoomStatus status;

    @Enumerated(EnumType.STRING)
    private RoomState state;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;
}