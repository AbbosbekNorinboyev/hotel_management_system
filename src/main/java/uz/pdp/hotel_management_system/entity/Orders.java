package uz.pdp.hotel_management_system.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "auth_user_id")
    private AuthUser authUser;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id")
    private Room room;
    private int numberOfPeople;
    private LocalDate beginDate;
    private LocalDate endDate;
}
