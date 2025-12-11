package uz.pdp.hotel_management_system.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.hotel_management_system.dto.response.Empty;
import uz.pdp.hotel_management_system.dto.response.ErrorResponse;
import uz.pdp.hotel_management_system.dto.response.Response;
import uz.pdp.hotel_management_system.entity.AuthUser;
import uz.pdp.hotel_management_system.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Override
    public ResponseEntity<?> me(AuthUser authUser) {
        if (authUser == null) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .message("USER IS NULL")
                    .code(HttpStatus.BAD_REQUEST.value())
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        Response<Object, Object> response = Response.builder()
                .success(true)
                .data(authUser)
                .error(Empty.builder().build())
                .build();

        return ResponseEntity.ok(response);
    }
}
