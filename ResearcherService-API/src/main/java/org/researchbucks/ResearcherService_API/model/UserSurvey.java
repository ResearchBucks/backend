package org.researchbucks.ResearcherService_API.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

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
}
