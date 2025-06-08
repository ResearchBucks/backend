package org.researchbucks.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.researchbucks.dto.PasswordResetDto;
import org.researchbucks.dto.ResponseDto;
import org.researchbucks.model.Respondent;
import org.researchbucks.repository.UserRepository;
import org.researchbucks.service.UserService;
import org.researchbucks.util.CommonMessages;
import org.researchbucks.util.SecurityUtil;
import org.researchbucks.util.jwt.*;
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
@RequestMapping("/respondent/auth")
@Slf4j
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenRevokeService tokenRevokeService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    /************************
     Respondent login
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
        RespondentDetails respondentDetails= (RespondentDetails) authentication.getPrincipal();
        if(!respondentDetails.isAccountNonLocked() || !respondentDetails.isEnabled()){
            return ResponseEntity.badRequest().body(
                    ResponseDto.builder()
                            .message(CommonMessages.INVALID_RESPONDENT)
                            .status(CommonMessages.RESPONSE_DTO_FAILED)
                            .build()
            );
        }
        String jwtToken = jwtUtil.generateTokenFromUsername(respondentDetails);
        List<String> roles = respondentDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        JwtAuthenticationResponse response = new JwtAuthenticationResponse().builder()
                                                            .token(jwtToken)
                                                            .email(respondentDetails.getUsername())
                                                            .id(respondentDetails.getId())
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
     Respondent logout
     Return type: ResponseEntity
     ************************/
    @PostMapping("/logout")
    public ResponseEntity<ResponseDto> logOutUser(HttpServletRequest request){
        String token = jwtUtil.getJwtFromHeader(request);
        long ttl = jwtUtil.getTTL(token);
        tokenRevokeService.blacklistToken(token, ttl);
        return ResponseEntity.ok().body(
                ResponseDto.builder()
                        .message(CommonMessages.LOGOUT)
                        .status(CommonMessages.RESPONSE_DTO_SUCCESS)
                        .build()
        );
    }

    /************************
     Verify respondent after email verification
     Return type: ResponseEntity
     ************************/
    @PostMapping("/verifyRespondent")
    public ResponseEntity<ResponseDto> verifyRespondent(@RequestBody PasswordResetDto passwordResetDto){
        try{
            if(!jwtUtil.validateJwtToken(passwordResetDto.getToken())) throw new Exception(CommonMessages.INVALID_JWT);
            log.info(CommonMessages.GET_RESPONDENT);
            Respondent respondent = userRepository.findByEmail(jwtUtil.getUserNameFromJwtToken(passwordResetDto.getToken()));
            if(respondent.getIsDeleted() || respondent.getIsVerified() || respondent.getVerificationToken() == null) throw new Exception(CommonMessages.INVALID_RESPONDENT);
            if(!respondent.getVerificationToken().equals(SecurityUtil.hashToken(passwordResetDto.getToken()))) throw new Exception(CommonMessages.INVALID_JWT);
            respondent.setIsVerified(true);
            respondent.setPassword(SecurityUtil.hashPassword(passwordResetDto.getPassword()));
            respondent.setVerificationToken(null);
            userRepository.save(respondent);
            log.info(CommonMessages.VERIFIED);
            return ResponseEntity.ok().body(
                    ResponseDto.builder()
                            .message(CommonMessages.VERIFIED)
                            .status(CommonMessages.RESPONSE_DTO_SUCCESS)
                            .data(respondent.getId())
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

    /************************
     Send password reset email
     Return type: ResponseEntity
     ************************/
    @PostMapping("/requestPasswordReset/{email}")
    public ResponseEntity<ResponseDto> requestPasswordReset(@PathVariable String email){
        return ResponseEntity.ok().body(
                userService.requestPasswordReset(email)
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
            log.info(CommonMessages.GET_RESPONDENT);
            Respondent respondent = userRepository.findByEmail(jwtUtil.getUserNameFromJwtToken(passwordResetDto.getToken()));
            if(respondent.getIsDeleted() || !respondent.getIsVerified() || respondent.getResetToken() == null) throw new Exception(CommonMessages.INVALID_RESPONDENT);
            if(!respondent.getResetToken().equals(SecurityUtil.hashToken(passwordResetDto.getToken()))) throw new Exception(CommonMessages.INVALID_JWT);
            respondent.setResetToken(null);
            respondent.setPassword(SecurityUtil.hashPassword(passwordResetDto.getPassword()));
            userRepository.save(respondent);
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
