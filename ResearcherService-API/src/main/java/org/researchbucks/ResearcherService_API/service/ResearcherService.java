package org.researchbucks.ResearcherService_API.service;

import org.researchbucks.ResearcherService_API.dto.ResearcherRegDto;
import org.researchbucks.ResearcherService_API.dto.ResponseDto;

public interface ResearcherService {

    ResponseDto registerResearcher(ResearcherRegDto researcherRegDto);

    ResponseDto verifyResearcher(ResearcherRegDto researcherRegDto);

    ResponseDto getAllRespondents();

    ResponseDto getResearcherById(Long id);
}
