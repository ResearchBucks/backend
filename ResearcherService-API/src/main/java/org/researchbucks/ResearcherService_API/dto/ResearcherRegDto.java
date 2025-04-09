package org.researchbucks.ResearcherService_API.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResearcherRegDto implements Serializable {

    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private String nic;
    private String address;
    private String password;
}
