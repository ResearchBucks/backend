package org.researchbucks.service;

import org.researchbucks.dto.RespondentUpdateDto;
import org.researchbucks.dto.ResponseDto;
import org.researchbucks.dto.UserRegDto;

public interface UserService {

    ResponseDto registerRespondent(UserRegDto userRegDto);
    
    ResponseDto getAllRespondents();

    ResponseDto getRespondentById(Long id);

    ResponseDto updateRespondent(Long id, RespondentUpdateDto respondentUpdateDto);

    ResponseDto deleteRespondent(Long id);

    ResponseDto requestPasswordReset(String email);
}
