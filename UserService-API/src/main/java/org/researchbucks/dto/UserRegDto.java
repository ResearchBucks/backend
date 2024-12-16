package org.researchbucks.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegDto {

    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private String nic;
    private String address;
}
