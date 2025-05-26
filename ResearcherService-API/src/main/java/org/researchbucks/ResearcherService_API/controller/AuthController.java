package org.researchbucks.ResearcherService_API.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.researchbucks.ResearcherService_API.dto.ResponseDto;
import org.researchbucks.ResearcherService_API.model.Researcher;
import org.researchbucks.ResearcherService_API.repository.ResearcherRepository;
import org.researchbucks.ResearcherService_API.util.CommonMessages;
import org.researchbucks.ResearcherService_API.util.SecurityUtil;
import org.researchbucks.ResearcherService_API.util.jwt.*;
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
@RequestMapping("/researcher/auth")
@Slf4j
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenRevokeService tokenRevokeService;
    @Autowired
    private ResearcherRepository researcherRepository;

    /************************
     Researcher login
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
        ResearcherDetails researcherDetails = (ResearcherDetails) authentication.getPrincipal();
        if(!researcherDetails.isAccountNonLocked() || !researcherDetails.isEnabled()){
            return ResponseEntity.badRequest().body(
                    ResponseDto.builder()
                            .message(CommonMessages.INVALID_RESEARCHER)
                            .status(CommonMessages.RESPONSE_DTO_FAILED)
                            .build()
            );
        }
        String jwtToken = jwtUtil.generateTokenFromUsername(researcherDetails);
        List<String> roles = researcherDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        JwtAuthenticationResponse response = new JwtAuthenticationResponse().builder()
                .token(jwtToken)
                .email(researcherDetails.getUsername())
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
     Researcher logout
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
     Verify researcher after email verification
     Return type: ResponseEntity
     ************************/
    @GetMapping("/verifyResearcher")
    public ResponseEntity<ResponseDto> verifyResearcher(@RequestParam("token") String token){
        try{
            if(!jwtUtil.validateJwtToken(token)) throw new Exception(CommonMessages.INVALID_JWT);
            log.info(CommonMessages.GET_RESEARCHER);
            Researcher researcher = researcherRepository.findResearcherByEmail(jwtUtil.getUserNameFromJwtToken(token));
            if(researcher.getIsDeleted() || researcher.getIsVerified() || researcher.getVerificationToken() == null) throw new Exception(CommonMessages.INVALID_RESEARCHER);
            if(!researcher.getVerificationToken().equals(SecurityUtil.hashToken(token))) throw new Exception(CommonMessages.INVALID_JWT);
            researcher.setIsVerified(true);
            researcher.setVerificationToken(null);
            researcherRepository.save(researcher);
            log.info(CommonMessages.VERIFIED);
            return ResponseEntity.ok().body(
                    ResponseDto.builder()
                            .message(CommonMessages.VERIFIED)
                            .status(CommonMessages.RESPONSE_DTO_SUCCESS)
                            .data(researcher.getId())
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
