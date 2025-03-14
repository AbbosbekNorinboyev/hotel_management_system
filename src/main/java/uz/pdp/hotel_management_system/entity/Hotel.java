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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String address;
    private String city;
    private String phoneNumber;
}
