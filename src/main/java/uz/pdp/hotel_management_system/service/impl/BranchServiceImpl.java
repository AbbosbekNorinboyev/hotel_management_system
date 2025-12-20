package uz.pdp.hotel_management_system.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.hotel_management_system.dto.BranchDto;
import uz.pdp.hotel_management_system.dto.response.Empty;
import uz.pdp.hotel_management_system.dto.response.Response;
import uz.pdp.hotel_management_system.entity.Branch;
import uz.pdp.hotel_management_system.exception.ResourceNotFoundException;
import uz.pdp.hotel_management_system.mapper.BranchMapper;
import uz.pdp.hotel_management_system.repository.BranchRepository;
import uz.pdp.hotel_management_system.service.BranchService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {
    private final BranchRepository branchRepository;
    private final BranchMapper branchMapper;

    @Override
    public ResponseEntity<?> addBranch(BranchDto branchDto) {
        Branch entity = branchMapper.toEntity(branchDto);
        entity.setCreatedAt(LocalDateTime.now());
        branchRepository.saveAndFlush(entity);

        var response = Response.builder()
                .success(true)
                .data(entity)
                .error(Empty.builder().build())
                .build();
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> getBranch(Long id) {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found: " + id));

        var response = Response.builder()
                .success(true)
                .data(branch)
                .error(Empty.builder().build())
                .build();
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> getAllBranch(Pageable pageable) {
        List<Branch> branches = branchRepository.findAll(pageable).getContent();

        var response = Response.builder()
                .success(true)
                .data(branchMapper.dtoList(branches))
                .error(Empty.builder().build())
                .build();
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> updateBranch(BranchDto branchDto) {
        Branch branch = branchRepository.findById(branchDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found: " + branchDto.getId()));
        branchMapper.update(branch, branchDto);
        branchRepository.saveAndFlush(branch);

        var response = Response.builder()
                .success(true)
                .data(branch)
                .error(Empty.builder().build())
                .build();
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> deleteBranch(Long id) {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found: " + id));
        branchRepository.delete(branch);

        var response = Response.builder()
                .success(true)
                .data(branch)
                .error(Empty.builder().build())
                .build();
        return ResponseEntity.ok(response);
    }
}
