package org.researchbucks.AdminService_API.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Data
@Builder
@Document(collection = "survey_questions")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SurveyQuestion {

    @Id
    private String id;
    private Long surveyId;
    private Map<Integer, Object> questions;
    private Long researcherId;
}
