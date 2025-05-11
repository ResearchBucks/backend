package org.researchbucks.AdminService_API.controller;

import lombok.extern.slf4j.Slf4j;
import org.researchbucks.AdminService_API.dto.AdminRegDto;
import org.researchbucks.AdminService_API.dto.AdminUpdateDto;
import org.researchbucks.AdminService_API.dto.ResponseDto;
import org.researchbucks.AdminService_API.repository.AdminRepository;
import org.researchbucks.AdminService_API.service.AdminService;
import org.researchbucks.AdminService_API.util.CommonMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/admin-panel")
@Slf4j
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private AdminRepository adminRepository;

    /************************
     Register an Admin(Only allow for super-admins)
     Return type: ResponseEntity
     ************************/
    @PostMapping("/register")
    public ResponseEntity<ResponseDto> createAdmin(@RequestBody AdminRegDto adminRegDto) {
        if(adminRepository.existsAdminByEmail(adminRegDto.getEmail())) {
            log.error(CommonMessages.ADMIN_EXISTS);
            return ResponseEntity.badRequest().body(
                    ResponseDto.builder()
                            .message(CommonMessages.ADMIN_EXISTS)
                            .status(CommonMessages.RESPONSE_DTO_FAILED)
                            .build()
            );
        }else {
            return ResponseEntity.created(null).body(
                    adminService.createAdmin(adminRegDto)
            );
        }
    }

    /************************
     Get all Admins
     Return type: ResponseEntity
     ************************/
    @GetMapping("/getAllAdmins")
    private ResponseEntity<ResponseDto> getAllAdmins(){
       return ResponseEntity.ok().body(
                adminService.getAllAdmins()
        );
    }

    /************************
     Get a specific Admin using adminId
     Return type: ResponseEntity
     ************************/
    @GetMapping("/getAdminById/{adminId}")
    private ResponseEntity<ResponseDto> getAdminById(@PathVariable Long adminId){
        return ResponseEntity.ok().body(
                adminService.getAdminById(adminId)
        );
    }

    /************************
     Delete an Admin using adminId(Only allow for super-admins)
     Return type: ResponseEntity
     ************************/
    @DeleteMapping("/deleteAdmin/{adminId}")
    private ResponseEntity<ResponseDto> deleteAdmin(@PathVariable Long adminId){
        return ResponseEntity.ok().body(
                adminService.deleteAdmin(adminId)
        );
    }

    /************************
     Update an Admin using adminId
     Return type: ResponseEntity
     ************************/
    @PutMapping("/updateAdmin/{adminId}")
    private ResponseEntity<ResponseDto> updateAdmin(@RequestBody AdminUpdateDto adminUpdateDto, @PathVariable Long adminId){
        return ResponseEntity.ok().body(
                adminService.updateAdmin(adminUpdateDto, adminId)
        );
    }

    /************************
     Update an Admin role using adminId(Only allow for super-admins)
     Return type: ResponseEntity
     ************************/
    @PutMapping("/makeSuperAdmin/{adminId}")
    private ResponseEntity<ResponseDto> changeAdminRole(@PathVariable Long adminId){
        return ResponseEntity.ok().body(
                adminService.changeAdminRole(adminId)
        );
    }
}
