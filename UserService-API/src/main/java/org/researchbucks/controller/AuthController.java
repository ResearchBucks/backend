package org.researchbucks.controller;

import org.researchbucks.dto.ResponseDto;
import org.researchbucks.util.CommonMessages;
import org.researchbucks.util.jwt.JwtAuthenticationRequest;
import org.researchbucks.util.jwt.JwtAuthenticationResponse;
import org.researchbucks.util.jwt.JwtUtil;
import org.researchbucks.util.jwt.RespondentDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/respondent/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;


    @GetMapping("/login")
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
}
