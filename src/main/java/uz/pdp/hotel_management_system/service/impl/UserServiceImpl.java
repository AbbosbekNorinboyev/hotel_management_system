package uz.pdp.hotel_management_system.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.hotel_management_system.dto.response.Empty;
import uz.pdp.hotel_management_system.dto.response.ErrorResponse;
import uz.pdp.hotel_management_system.dto.response.Response;
import uz.pdp.hotel_management_system.entity.AuthUser;
import uz.pdp.hotel_management_system.repository.AuthUserRepository;
import uz.pdp.hotel_management_system.service.UserService;
import uz.pdp.hotel_management_system.service.logic.RedisCacheService;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final AuthUserRepository authUserRepository;
    private final RedisCacheService redisCacheService;
    private static final String CACHE = "users";

    @Override
    public ResponseEntity<?> me(AuthUser authUser) {
        if (authUser == null) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .message("USER IS NULL")
                    .code(HttpStatus.BAD_REQUEST.value())
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        var response = Response.builder()
                .success(true)
                .data(authUser)
                .error(Empty.builder().build())
                .build();
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> getAll(Pageable pageable) {
        List<AuthUser> authUsers = authUserRepository.findAll(pageable).getContent();
        redisCacheService.saveData(CACHE, authUsers, 10, TimeUnit.MINUTES);

        var response = Response.builder()
                .success(true)
                .data(redisCacheService.getData(CACHE))
                .error(Empty.builder().build())
                .build();
        return ResponseEntity.ok(response);
    }
}
