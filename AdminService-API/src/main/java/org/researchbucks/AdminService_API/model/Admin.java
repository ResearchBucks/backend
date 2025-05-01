package org.researchbucks.AdminService_API.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.researchbucks.AdminService_API.enums.AdminRole;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "admin")
public class Admin implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Email
    @Column(name = "email", nullable = false)
    private String email;

    @JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "created_date", nullable = false)
    private Date createdDate;

    @Column(name = "created_admin", nullable = false)
    private Long createdAdminId;

    @Column(name = "last_login")
    private Date lastLogin;

    @Column(name = "role", nullable = false)
    private AdminRole role;
}
