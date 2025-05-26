package org.researchbucks.AdminService_API.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.researchbucks.AdminService_API.dto.PasswordResetDto;
import org.researchbucks.AdminService_API.dto.ResponseDto;
import org.researchbucks.AdminService_API.model.Admin;
import org.researchbucks.AdminService_API.repository.AdminRepository;
import org.researchbucks.AdminService_API.service.AdminService;
import org.researchbucks.AdminService_API.util.CommonMessages;
import org.researchbucks.AdminService_API.util.SecurityUtil;
import org.researchbucks.AdminService_API.util.jwt.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/auth")
@Slf4j
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenRevokeService tokenRevokeService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private AdminRepository adminRepository;

    /************************
     Admin login
     Return type: ResponseEntity(token and other data)
     ************************/
    @PostMapping("/login")
    public ResponseEntity<ResponseDto> authenticateUser(@RequestBody JwtAuthenticationRequest request) {
        Authentication authentication;
        try {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (AuthenticationException exception) {
            return ResponseEntity.badRequest().body(
                    ResponseDto.builder()
                            .message(CommonMessages.BAD_CREDENTIALS)
                            .status(CommonMessages.RESPONSE_DTO_FAILED)
                            .build()
            );
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        AdminDetails adminDetails = (AdminDetails) authentication.getPrincipal();
        if(!adminDetails.isEnabled()){
            return ResponseEntity.badRequest().body(
                    ResponseDto.builder()
                            .message(CommonMessages.INVALID_ADMIN)
                            .status(CommonMessages.RESPONSE_DTO_FAILED)
                            .build()
            );
        }
        String jwtToken = jwtUtil.generateTokenFromUsername(adminDetails);
        List<String> roles = adminDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        JwtAuthenticationResponse response = new JwtAuthenticationResponse().builder()
                .token(jwtToken)
                .email(adminDetails.getUsername())
                .roles(roles)
                .build();
        return ResponseEntity.ok().body(
                ResponseDto.builder()
                        .message(CommonMessages.AUTHENTICATED)
                        .status(CommonMessages.RESPONSE_DTO_SUCCESS)
                        .data(response)
                        .build()
        );
    }

    /************************
     Admin logout
     Return type: ResponseEntity
     ************************/
    @PostMapping("/logout")
    public ResponseEntity<ResponseDto> logOutUser(HttpServletRequest request){
        String token = jwtUtil.getJwtFromHeader(request);
        long ttl = jwtUtil.getTTL(token);
        String email = jwtUtil.getUserNameFromJwtToken(token);
        tokenRevokeService.blacklistToken(token, ttl);
        adminService.markLastLogin(email);
        return ResponseEntity.ok().body(
                ResponseDto.builder()
                        .message(CommonMessages.LOGOUT)
                        .status(CommonMessages.RESPONSE_DTO_SUCCESS)
                        .build()
        );
    }

    /************************
     Send password reset email
     Return type: ResponseEntity
     ************************/
    @PostMapping("/requestPasswordReset/{email}")
    public ResponseEntity<ResponseDto> requestPasswordReset(@PathVariable String email){
        return ResponseEntity.ok().body(
                adminService.requestPasswordReset(email)
        );
    }

    /************************
     Validate password reset token
     Return type: ResponseEntity
     ************************/
    @PostMapping("/resetPassword")
    public ResponseEntity<ResponseDto> resetPassword(@RequestBody PasswordResetDto passwordResetDto){
        try{
            if(!jwtUtil.validateJwtToken(passwordResetDto.getToken())) throw new Exception(CommonMessages.INVALID_JWT);
            log.info(CommonMessages.GET_ADMIN);
            Admin admin = adminRepository.findByEmail(jwtUtil.getUserNameFromJwtToken(passwordResetDto.getToken()));
            if(admin.getIsDeleted() || admin.getResetToken() == null) throw new Exception(CommonMessages.INVALID_ADMIN);
            if(!admin.getResetToken().equals(SecurityUtil.hashToken(passwordResetDto.getToken()))) throw new Exception(CommonMessages.INVALID_JWT);
            admin.setResetToken(null);
            admin.setPassword(SecurityUtil.hashPassword(passwordResetDto.getPassword()));
            adminRepository.save(admin);
            long ttl = jwtUtil.getTTL(passwordResetDto.getToken());
            tokenRevokeService.blacklistToken(passwordResetDto.getToken(), ttl);
            log.info(CommonMessages.PASS_R_SUCCESS);
            return ResponseEntity.ok().body(
                    ResponseDto.builder()
                            .message(CommonMessages.PASS_R_SUCCESS)
                            .status(CommonMessages.RESPONSE_DTO_SUCCESS)
                            .build()
            );
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(
                    ResponseDto.builder()
                            .message(e.getMessage())
                            .status(CommonMessages.RESPONSE_DTO_FAILED)
                            .build()
            );
        }
    }
}
