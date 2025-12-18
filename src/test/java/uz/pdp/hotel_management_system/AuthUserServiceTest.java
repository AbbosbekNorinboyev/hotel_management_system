package uz.pdp.hotel_management_system;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import uz.pdp.hotel_management_system.entity.AuthUser;
import uz.pdp.hotel_management_system.enums.Role;
import uz.pdp.hotel_management_system.repository.AuthUserRepository;
import uz.pdp.hotel_management_system.service.AuthUserService;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AuthUserServiceTest {
    @Mock
    private AuthUserRepository authUserRepository; // Mock obyekt
    @InjectMocks
    private AuthUserService authUserService; // Mock dependency-lar injektsiya qilinadi

    @Test
    void testGetUserNameByIdSuccess() {
        // Given
        AuthUser authUser = AuthUser.builder()
                .id(1L)
                .username("abbos")
                .role(Role.ADMIN)
                .password("2210")
                .build();
        Mockito.when(authUserRepository.findById(1L)).thenReturn(Optional.of(authUser));

        // When
        String username = authUserService.getUserNameById(1L);

        // Then
        Assertions.assertEquals("abbos", username);
    }

    @Test
    void testGetUserNameByIdNotFound() {
        // Given
        Mockito.when(authUserRepository.findById(2L)).thenReturn(Optional.empty());

        // When & Then
        Assertions.assertThrows(RuntimeException.class, () -> authUserService.getUserNameById(2L));
    }
}
