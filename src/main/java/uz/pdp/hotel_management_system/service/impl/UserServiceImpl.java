package uz.pdp.hotel_management_system.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.hotel_management_system.dto.response.ResponseDTO;
import uz.pdp.hotel_management_system.entity.AuthUser;
import uz.pdp.hotel_management_system.service.UserService;

import java.time.LocalDateTime;

import static uz.pdp.hotel_management_system.utils.Util.localDateTimeFormatter;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Override
    public ResponseDTO<?> me(AuthUser authUser) {
        if (authUser == null) {
            return ResponseDTO.builder()
                    .message("USER IS NULL")
                    .build();
        }
        return ResponseDTO.builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("AuthUser successfully found")
                .data(authUser)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }
}
