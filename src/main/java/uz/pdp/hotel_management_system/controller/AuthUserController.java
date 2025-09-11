package uz.pdp.hotel_management_system.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import uz.pdp.hotel_management_system.config.CustomUserDetailsService;
import uz.pdp.hotel_management_system.dto.AuthUserDto;
import uz.pdp.hotel_management_system.dto.LoginRequest;
import uz.pdp.hotel_management_system.entity.AuthUser;
import uz.pdp.hotel_management_system.enums.Role;
import uz.pdp.hotel_management_system.exception.ResourceNotFoundException;
import uz.pdp.hotel_management_system.repository.AuthUserRepository;
import uz.pdp.hotel_management_system.utils.JWTUtils;

import java.util.Optional;

import static uz.pdp.hotel_management_system.utils.PasswordHasher.hashPassword;
import static uz.pdp.hotel_management_system.utils.PasswordValidator.validatePassword;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@CrossOrigin("http://localhost:1173")
public class AuthUserController {
    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final JWTUtils jwtUtils;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AuthUserDto authUserDto) {
        Optional<AuthUser> byUsername = authUserRepository.findByUsername(authUserDto.getUsername());
        if (byUsername.isPresent()) {
            return ResponseEntity.badRequest().body("Username is already exists");
        }
        AuthUser authUser = new AuthUser();
        authUser.setUsername(authUser.getUsername());
        authUser.setPassword(hashPassword(authUserDto.getPassword()));
        authUser.setRole(Role.USER);
        authUserRepository.save(authUser);
        return ResponseEntity.ok("Auth user successfully created");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        AuthUser authUser = authUserRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + loginRequest.getUsername()));
        if (authUser.getUsername() == null) {
            return ResponseEntity.badRequest().body("Username not found");
        }
        if (!validatePassword(loginRequest.getPassword(), authUser.getPassword())) {
            return ResponseEntity.badRequest().body("Invalid password");
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginRequest.getUsername());
        String jwtToken = jwtUtils.generateToken(userDetails.getUsername());
        return ResponseEntity.ok(jwtToken);
    }

    @GetMapping("/getUsername")
    public ResponseEntity<String> getUsername(@RequestParam String token) {
        String username = jwtUtils.generateToken(token);
        return ResponseEntity.ok(username);
    }
}
