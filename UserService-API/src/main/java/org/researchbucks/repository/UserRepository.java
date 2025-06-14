package org.researchbucks.repository;

import org.researchbucks.model.Respondent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Respondent, Long> {
    boolean existsRespondentByEmail(String email);

    boolean existsRespondentByNic(String nic);

    Respondent findByEmail(String email);
}
