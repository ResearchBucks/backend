package org.researchbucks.ResearcherService_API.service;

import org.researchbucks.ResearcherService_API.dto.ResearcherRegDto;
import org.researchbucks.ResearcherService_API.dto.ResearcherUpdateDto;
import org.researchbucks.ResearcherService_API.dto.ResponseDto;

public interface ResearcherService {

    ResponseDto registerResearcher(ResearcherRegDto researcherRegDto);

    ResponseDto getAllResearchers();

    ResponseDto getResearcherById(Long id);

    ResponseDto updateResearcher(Long id, ResearcherUpdateDto researcherUpdateDto);

    ResponseDto deleteResearcher(Long id);
}
