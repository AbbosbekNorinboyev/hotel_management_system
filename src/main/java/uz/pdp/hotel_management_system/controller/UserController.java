package uz.pdp.hotel_management_system.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.hotel_management_system.dto.response.ResponseDTO;
import uz.pdp.hotel_management_system.entity.AuthUser;
import uz.pdp.hotel_management_system.service.UserService;
import uz.pdp.hotel_management_system.utils.validator.CurrentUser;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public ResponseDTO<?> me(@CurrentUser AuthUser authUser) {
        return userService.me(authUser);
    }
}
