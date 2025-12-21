package uz.pdp.hotel_management_system.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.hotel_management_system.dto.response.Empty;
import uz.pdp.hotel_management_system.dto.response.ErrorResponse;
import uz.pdp.hotel_management_system.dto.response.Response;
import uz.pdp.hotel_management_system.entity.Attachment;
import uz.pdp.hotel_management_system.entity.AuthUser;
import uz.pdp.hotel_management_system.exception.ResourceNotFoundException;
import uz.pdp.hotel_management_system.repository.AttachmentRepository;
import uz.pdp.hotel_management_system.repository.AuthUserRepository;
import uz.pdp.hotel_management_system.service.UserService;
import uz.pdp.hotel_management_system.service.logic.RedisCacheService;
import uz.pdp.hotel_management_system.utils.Util;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final AuthUserRepository authUserRepository;
    private final RedisCacheService redisCacheService;
    private final AttachmentRepository attachmentRepository;
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

    @Override
    public ResponseEntity<?> addPicture(Long userId, MultipartFile file) {
        AuthUser authUser = authUserRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));
        if (file != null && !file.isEmpty()) {
            Attachment attachment = Util.getUrl(file, attachmentRepository);
            if (attachment != null) {
                authUser.setAttachment(attachment);
                authUserRepository.save(authUser);
            }
        }

        var response = Response.builder()
                .success(true)
                .data(authUser)
                .error(Empty.builder().build())
                .build();
        return ResponseEntity.ok(response);
    }
}
