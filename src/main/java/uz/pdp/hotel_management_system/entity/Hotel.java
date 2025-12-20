package uz.pdp.hotel_management_system.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hotel_id_seq")
    @SequenceGenerator(name = "hotel_id_seq", sequenceName = "hotel_id_seq", allocationSize = 1)
    private Long id;

    private String name;
    private String address;
    private String city;
    private String phoneNumber;
}
