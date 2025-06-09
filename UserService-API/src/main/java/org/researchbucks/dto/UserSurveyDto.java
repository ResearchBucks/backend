package org.researchbucks.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.researchbucks.model.UserSurvey;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSurveyDto {

    private List<UserSurvey> answeredSurveys;
    private List<UserSurvey> notAnsweredSurveys;
}
