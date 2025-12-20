package uz.pdp.hotel_management_system.mapper;

import org.springframework.stereotype.Component;
import uz.pdp.hotel_management_system.dto.BranchDto;
import uz.pdp.hotel_management_system.entity.Branch;

import java.util.ArrayList;
import java.util.List;

@Component
public class BranchMapper {
    public Branch toEntity(BranchDto dto) {
        return Branch.builder()
                .id(dto.getId())
                .name(dto.getName())
                .code(dto.getCode())
                .address(dto.getAddress())
                .city(dto.getCity())
                .country(dto.getCountry())
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .active(dto.getActive())
                .build();
    }

    public BranchDto toDto(Branch entity) {
        return BranchDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .code(entity.getCode())
                .address(entity.getAddress())
                .city(entity.getCity())
                .country(entity.getCountry())
                .phone(entity.getPhone())
                .email(entity.getEmail())
                .active(entity.getActive())
                .build();
    }

    public List<BranchDto> dtoList(List<Branch> list) {
        if (list != null && !list.isEmpty()) {
            return list.stream().map(this::toDto).toList();
        }
        return new ArrayList<>();
    }

    public void update(Branch entity, BranchDto dto) {
        if (dto == null) {
            return;
        }
        if (dto.getName() != null && !dto.getName().trim().isEmpty()) {
            entity.setName(dto.getName());
        }
        if (dto.getCode() != null && !dto.getCode().trim().isEmpty()) {
            entity.setCode(dto.getCode());
        }
        if (dto.getAddress() != null && !dto.getAddress().trim().isEmpty()) {
            entity.setAddress(dto.getAddress());
        }
        if (dto.getCity() != null && !dto.getCity().trim().isEmpty()) {
            entity.setCity(dto.getCity());
        }
        if (dto.getCountry() != null && !dto.getCountry().trim().isEmpty()) {
            entity.setCountry(dto.getCountry());
        }
        if (dto.getPhone() != null && !dto.getPhone().trim().isEmpty()) {
            entity.setPhone(dto.getPhone());
        }
        if (dto.getEmail() != null && !dto.getEmail().trim().isEmpty()) {
            entity.setEmail(dto.getEmail());
        }
        if (dto.getActive() != null) {
            entity.setActive(dto.getActive());
        }
    }
}
