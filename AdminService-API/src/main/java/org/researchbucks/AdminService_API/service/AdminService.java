package org.researchbucks.AdminService_API.service;

import org.researchbucks.AdminService_API.dto.AdminRegDto;
import org.researchbucks.AdminService_API.dto.AdminUpdateDto;
import org.researchbucks.AdminService_API.dto.ResponseDto;

public interface AdminService {

    ResponseDto createAdmin(AdminRegDto adminRegDto);

    ResponseDto getAllAdmins();

    ResponseDto getAdminById(Long adminId);

    ResponseDto deleteAdmin(Long adminId);

    ResponseDto updateAdmin(AdminUpdateDto adminUpdateDto, Long adminId);

    ResponseDto changeAdminRole(Long adminId);
}
