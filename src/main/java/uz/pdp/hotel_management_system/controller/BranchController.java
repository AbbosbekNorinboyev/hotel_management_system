package uz.pdp.hotel_management_system.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.hotel_management_system.dto.BranchDto;
import uz.pdp.hotel_management_system.entity.Branch;
import uz.pdp.hotel_management_system.service.BranchService;

@RestController
@RequestMapping("/api/branches")
@RequiredArgsConstructor
public class BranchController {
    private final BranchService branchService;

    @PostMapping("/add")
    public ResponseEntity<?> addBranch(@RequestBody BranchDto branchDto) {
        return branchService.addBranch(branchDto);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getBranch(@RequestParam Long id) {
        return branchService.getBranch(id);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllBranch(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                          @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        return branchService.getAllBranch(PageRequest.of(page, size));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateBranch(@RequestBody BranchDto branchDto) {
        return branchService.updateBranch(branchDto);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteBranch(@RequestParam Long id) {
        return branchService.deleteBranch(id);
    }
}
