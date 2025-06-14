package org.researchbucks.AdminService_API.model;

import jakarta.persistence.*;
import lombok.*;
import org.researchbucks.AdminService_API.enums.PaymentStatus;

import java.io.Serializable;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "survey")
public class Survey implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "expiring_date")
    private Date expiringDate;

    @Column(name = "last_edited")
    private Date lastEditedDate;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private PaymentStatus paymentStatus;

    @Column(name = "is_verified", nullable = false)
    private Boolean isVerified;

    @Column(name = "payment_per_user", nullable = false)
    private Integer paymentPerUser;

    @Column(name = "survey_price")
    private Integer surveyPrice;

    @Column(name = "remaining_amount", nullable = false)
    private Integer remainingAmountToPay;

    @Column(name = "paid_date")
    private Date paidDate;

    @Column(name = "approved_date")
    private Date approvedDate;

    @Column(name = "description")
    private String description;

    @Column(name = "is_rejected")
    private Boolean isRejected;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "created_by")
    private Researcher researcher;
}







