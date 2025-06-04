package org.researchbucks.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_survey")
public class UserSurvey implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "expired_at", nullable = false)
    private Date expiredAt;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "paid_to_respondents", nullable = false)
    private Boolean isPaidToRespondents;

    @Column(name = "is_verified")
    private Boolean isVerified;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "payment_per_user")
    private Integer paymentPerUser;

    @JsonBackReference
    @ToString.Exclude
    @ManyToMany
    @JoinTable(name = "survey_respondents",
            joinColumns = @JoinColumn(name = "survey_id"),
            inverseJoinColumns = @JoinColumn(name = "respondents_id"))
    private List<Respondent> respondents = new ArrayList<>();

    public void addRespondent(Respondent respondent) {
        this.respondents.add(respondent);
        respondent.getSurvey().add(this);
    }

}
