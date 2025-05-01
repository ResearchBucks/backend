package org.researchbucks.AdminService_API.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.researchbucks.AdminService_API.enums.AdminRole;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminRegDto implements Serializable {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Long createdBy;
    private AdminRole role;
}
