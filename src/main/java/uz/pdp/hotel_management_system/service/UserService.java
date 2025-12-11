package uz.pdp.hotel_management_system.service;

import org.springframework.http.ResponseEntity;
import uz.pdp.hotel_management_system.entity.AuthUser;

public interface UserService {
    ResponseEntity<?> me(AuthUser authUser);
}
