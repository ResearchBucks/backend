package org.researchbucks.AdminService_API.repository;

import org.researchbucks.AdminService_API.model.UserSurvey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSurveyRepository extends JpaRepository<UserSurvey, Long> {

}
