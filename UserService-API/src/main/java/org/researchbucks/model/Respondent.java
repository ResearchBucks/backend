package org.researchbucks.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.researchbucks.util.CommonMessages;

import java.io.Serializable;
import java.util.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "respondent")
public class Respondent implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Email(message = CommonMessages.INVALID_EMAIL)
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "mobile_number", nullable = false)
    private String mobile;

    @Column(name = "nic")
    private String nic;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "created_date")
    private Date createdDate;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "total_earnings")
    private Integer totalEarnings;

    @Column(name = "is_verified")
    private Boolean isVerified;

    @Column(name = "is_locked")
    private Boolean isLocked;

    @Column(name = "role", nullable = false)
    private Role role;

    @JsonManagedReference
    @ToString.Exclude
    @ManyToMany(mappedBy = "respondents")
    private List<UserSurvey> survey = new ArrayList<>();

}
