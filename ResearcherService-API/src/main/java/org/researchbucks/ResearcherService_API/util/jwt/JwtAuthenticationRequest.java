package org.researchbucks.ResearcherService_API.util.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthenticationRequest {

    private String email;
    private String password;
}
