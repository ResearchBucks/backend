package org.researchbucks.ResearcherService_API.repository;

import org.researchbucks.ResearcherService_API.model.Researcher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResearcherRepository extends JpaRepository<Researcher, Long> {

    boolean existsResearcherByEmail(String email);

    boolean existsResearcherByNic(String nic);

    Researcher findResearcherByEmail(String email);
}
