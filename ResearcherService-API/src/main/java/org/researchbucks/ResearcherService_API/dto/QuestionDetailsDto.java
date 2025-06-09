package org.researchbucks.ResearcherService_API.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.researchbucks.ResearcherService_API.enums.QuestionType;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDetailsDto {

    private String question;
    private String sinQuestion;
    private Boolean isRequired;
    private QuestionType type;
    private List<QuestionOptionDto> options;
}
