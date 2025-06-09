package org.researchbucks.ResearcherService_API.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mongodb.annotations.Immutable;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Data
@Builder
@Document(collection = "survey_responses")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SurveyData {

    @Id
    private String id;
    private Long surveyId;
    private Map<Integer, Object> answers;
    private Long userId;
}
