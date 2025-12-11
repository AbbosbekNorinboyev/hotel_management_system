package uz.pdp.hotel_management_system.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.hotel_management_system.entity.AuthUser;
import uz.pdp.hotel_management_system.repository.AuthUserRepository;

@Service
@RequiredArgsConstructor
public class AuthUserService {
    private final AuthUserRepository authUserRepository;

    public String getUserNameById(Integer id) {
        return authUserRepository.findById(id)
                .map(AuthUser::getUsername)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
