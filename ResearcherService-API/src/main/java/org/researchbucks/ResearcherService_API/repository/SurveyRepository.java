package org.researchbucks.ResearcherService_API.repository;

import org.researchbucks.ResearcherService_API.model.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {

    List<Survey> getAllByResearcherIdAndIsDeletedFalse(Long researcherId);
}
