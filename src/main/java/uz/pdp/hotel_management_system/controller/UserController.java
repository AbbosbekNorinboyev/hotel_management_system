package uz.pdp.hotel_management_system.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.hotel_management_system.entity.AuthUser;
import uz.pdp.hotel_management_system.service.UserService;
import uz.pdp.hotel_management_system.utils.validator.CurrentUser;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<?> me(@CurrentUser AuthUser authUser) {
        return userService.me(authUser);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                    @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        return userService.getAll(PageRequest.of(page, size));
    }

    @PostMapping(value = "/addPicture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addPicture(@RequestParam Long userId,
                                        @RequestBody MultipartFile file) {
        return userService.addPicture(userId, file);
    }
}
