package org.researchbucks.ResearcherService_API.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.researchbucks.ResearcherService_API.enums.PaymentStatus;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentUpdateDto implements Serializable {

    private Integer paidAmount;
    private PaymentStatus paymentStatus;
}
