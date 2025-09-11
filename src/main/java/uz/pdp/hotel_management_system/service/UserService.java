package uz.pdp.hotel_management_system.service;

import uz.pdp.hotel_management_system.dto.response.ResponseDTO;
import uz.pdp.hotel_management_system.entity.AuthUser;

public interface UserService {
    ResponseDTO<?> me(AuthUser authUser);
}
