package uz.pdp.hotel_management_system.service;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.hotel_management_system.entity.AuthUser;

public interface UserService {
    ResponseEntity<?> me(AuthUser authUser);

    ResponseEntity<?> getAll(Pageable pageable);

    ResponseEntity<?> addPicture(Long userId, MultipartFile file);
}
