package org.researchbucks.AdminService_API.repository;

import org.researchbucks.AdminService_API.model.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SurveyRepository extends JpaRepository<Survey, Long> {

    List<Survey> getAllByIsDeletedIsFalse();
}
