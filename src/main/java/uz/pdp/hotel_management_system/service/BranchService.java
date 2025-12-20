package uz.pdp.hotel_management_system.service;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import uz.pdp.hotel_management_system.dto.BranchDto;

public interface BranchService {
    ResponseEntity<?> addBranch(BranchDto branchDto);

    ResponseEntity<?> getBranch(Long id);

    ResponseEntity<?> getAllBranch(Pageable pageable);

    ResponseEntity<?> updateBranch(BranchDto branchDto);

    ResponseEntity<?> deleteBranch(Long id);
}
