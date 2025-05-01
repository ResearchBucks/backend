package org.researchbucks.AdminService_API.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminUpdateDto implements Serializable {

    private String firstName;
    private String lastName;
    private String password;
}
