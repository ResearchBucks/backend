package org.researchbucks.AdminService_API.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.annotations.Immutable;
import org.researchbucks.AdminService_API.util.CommonMessages;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Immutable
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "researcher")
public class Researcher implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "occupation")
    private String occupation;

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

    @Column(name = "is_verified")
    private Boolean isVerified;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "researcher", orphanRemoval = true)
    private List<Survey> surveys = new ArrayList<>();
}
