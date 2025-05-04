package org.researchbucks.AdminService_API.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.researchbucks.AdminService_API.dto.AdminRegDto;
import org.researchbucks.AdminService_API.dto.AdminUpdateDto;
import org.researchbucks.AdminService_API.dto.ResponseDto;
import org.researchbucks.AdminService_API.enums.AdminRole;
import org.researchbucks.AdminService_API.model.Admin;
import org.researchbucks.AdminService_API.repository.AdminRepository;
import org.researchbucks.AdminService_API.service.AdminService;
import org.researchbucks.AdminService_API.util.CommonMessages;
import org.researchbucks.AdminService_API.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public ResponseDto createAdmin(AdminRegDto adminRegDto) {
        try{
            if(!adminRepository.existsAdminById(adminRegDto.getCreatedBy())) throw new Exception(CommonMessages.INVALID_C_ADMIN);
            Admin admin = new Admin().builder()
                    .firstName(adminRegDto.getFirstName())
                    .lastName(adminRegDto.getLastName())
                    .email(adminRegDto.getEmail())
                    .password(SecurityUtil.hashPassword(adminRegDto.getPassword()))
                    .isDeleted(false)
                    .createdDate(new Date())
                    .createdAdminId(adminRegDto.getCreatedBy())
                    .role(adminRegDto.getRole())
                    .build();
            adminRepository.save(admin);
            log.info(CommonMessages.ADMIN_SUCCESS);
            return ResponseDto.builder()
                    .message(CommonMessages.ADMIN_SUCCESS)
                    .status(CommonMessages.RESPONSE_DTO_SUCCESS)
                    .data(admin.getId())
                    .build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseDto.builder()
                    .message(e.getMessage())
                    .status(CommonMessages.RESPONSE_DTO_FAILED)
                    .build();
        }
    }

    @Override
    public ResponseDto getAllAdmins() {
        try {
            log.info(CommonMessages.GET_ADMIN);
            ResponseDto responseDto = ResponseDto.builder()
                    .message(CommonMessages.RESPONSE_DTO_SUCCESS)
                    .status(CommonMessages.RESPONSE_DTO_SUCCESS)
                    .data(adminRepository.findAll())
                    .build();
            log.info(CommonMessages.GET_ADMIN_SUCCESS);
            return responseDto;
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseDto.builder()
                    .message(e.getMessage())
                    .status(CommonMessages.RESPONSE_DTO_FAILED)
                    .build();
        }
    }

    @Override
    public ResponseDto getAdminById(Long adminId) {
        try{
            log.info(CommonMessages.GET_ADMIN);
            Admin admin = adminRepository.findById(adminId).get();
            if(admin.getIsDeleted()) throw new Exception(CommonMessages.INVALID_ADMIN);
            ResponseDto responseDto = ResponseDto.builder()
                    .message(CommonMessages.RESPONSE_DTO_SUCCESS)
                    .status(CommonMessages.RESPONSE_DTO_SUCCESS)
                    .data(admin)
                    .build();
            log.info(CommonMessages.GET_ADMIN_SUCCESS);
            return responseDto;
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseDto.builder()
                    .message(e.getMessage())
                    .status(CommonMessages.RESPONSE_DTO_FAILED)
                    .build();
        }
    }

    @Override
    public ResponseDto deleteAdmin(Long adminId) {
        try {
            log.info(CommonMessages.GET_ADMIN);
            Admin admin =  adminRepository.findById(adminId).get();
            if(admin.getIsDeleted()) throw new Exception(CommonMessages.INVALID_ADMIN);
            admin.setIsDeleted(true);
            adminRepository.save(admin);
            log.info(CommonMessages.ADMIN_DELETE_SUCCESS);
            return ResponseDto.builder()
                    .message(CommonMessages.ADMIN_DELETE_SUCCESS)
                    .status(CommonMessages.RESPONSE_DTO_SUCCESS)
                    .build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseDto.builder()
                    .message(e.getMessage())
                    .status(CommonMessages.RESPONSE_DTO_FAILED)
                    .build();
        }
    }

    @Override
    public ResponseDto updateAdmin(AdminUpdateDto adminUpdateDto, Long adminId) {
        try{
            log.info(CommonMessages.GET_ADMIN);
            Admin admin =  adminRepository.findById(adminId).get();
            if(admin.getIsDeleted()) throw new Exception(CommonMessages.INVALID_ADMIN);
            if(adminUpdateDto.getFirstName() != null) admin.setFirstName(adminUpdateDto.getFirstName());
            if(adminUpdateDto.getLastName() != null) admin.setLastName(adminUpdateDto.getLastName());
            if(adminUpdateDto.getPassword() != null) admin.setPassword(SecurityUtil.hashPassword(adminUpdateDto.getPassword()));
            adminRepository.save(admin);
            log.info(CommonMessages.ADMIN_UPDATED);
            return ResponseDto.builder()
                    .message(CommonMessages.ADMIN_UPDATED)
                    .status(CommonMessages.RESPONSE_DTO_SUCCESS)
                    .build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseDto.builder()
                    .message(e.getMessage())
                    .status(CommonMessages.RESPONSE_DTO_FAILED)
                    .build();
        }
    }

    @Override
    public ResponseDto changeAdminRole(Long adminId) {
        try {
            log.info(CommonMessages.GET_ADMIN);
            Admin admin =  adminRepository.findById(adminId).get();
            if(admin.getIsDeleted()) throw new Exception(CommonMessages.INVALID_ADMIN);
            admin.setRole(AdminRole.SUPER_ADMIN);
            adminRepository.save(admin);
            log.info(CommonMessages.ADMIN_R_UPDATED);
            return ResponseDto.builder()
                    .message(CommonMessages.ADMIN_R_UPDATED)
                    .status(CommonMessages.RESPONSE_DTO_SUCCESS)
                    .build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseDto.builder()
                    .message(e.getMessage())
                    .status(CommonMessages.RESPONSE_DTO_FAILED)
                    .build();
        }
    }
}
