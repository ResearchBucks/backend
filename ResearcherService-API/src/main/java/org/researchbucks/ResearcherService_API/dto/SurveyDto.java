package org.researchbucks.ResearcherService_API.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.researchbucks.ResearcherService_API.model.PaymentStatus;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SurveyDto implements Serializable {

    private String title;
    private Integer paymentPerUser;
    private Date expiringDate;
    private PaymentStatus paymentStatus;
    private List<SurveyQuestionDto> surveyQuestionList;
}
