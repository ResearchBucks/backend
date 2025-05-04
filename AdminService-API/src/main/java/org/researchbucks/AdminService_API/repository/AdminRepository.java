package org.researchbucks.AdminService_API.repository;

import jakarta.validation.constraints.Email;
import org.researchbucks.AdminService_API.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    boolean existsAdminByEmail(@Email String email);

    boolean existsAdminById(Long id);
}
