package org.researchbucks.ResearcherService_API.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.researchbucks.ResearcherService_API.enums.PaymentStatus;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SurveyDto implements Serializable {

    private String title;
    private String description;
    private Integer paymentPerUser;
    private Integer surveyPrice;
    private Date expiringDate;
    private PaymentStatus paymentStatus;
    private List<SurveyQuestionDto> surveyQuestionList;
}
