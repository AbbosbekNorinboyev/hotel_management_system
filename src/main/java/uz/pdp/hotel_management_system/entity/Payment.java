package uz.pdp.hotel_management_system.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.hotel_management_system.enums.PaymentType;

import java.time.LocalDateTime;
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Double amount;
    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id")
    private Room room;
}
