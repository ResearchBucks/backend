package org.researchbucks.ResearcherService_API.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResearcherUpdateDto implements Serializable {

    private String firstName;
    private String lastName;
    private String mobile;
    private String password;
    private String address;
    private String occupation;
}
