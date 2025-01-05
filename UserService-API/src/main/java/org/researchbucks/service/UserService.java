package org.researchbucks.service;

import org.researchbucks.dto.ResponseDto;
import org.researchbucks.dto.UserRegDto;

public interface UserService {

    ResponseDto registerRespondent(UserRegDto userRegDto);
}
