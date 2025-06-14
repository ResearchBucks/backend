package org.researchbucks.AdminService_API.repository;

import org.researchbucks.AdminService_API.enums.PaymentStatus;
import org.researchbucks.AdminService_API.model.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {

    List<Survey> getAllByIsDeletedIsFalse();

    @Modifying
    @Transactional
    @Query("UPDATE Survey s SET s.isVerified = ?1, s.approvedDate = ?2 WHERE s.id = ?3")
    void approveSurvey(Boolean isVerified, Date approvedDate, Long surveyId);

    @Modifying
    @Transactional
    @Query("UPDATE Survey s SET s.paymentStatus = ?1 WHERE s.id = ?2")
    void updatePaymentStatus(PaymentStatus paymentStatus, Long surveyId);

    @Query("SELECT s.paymentPerUser FROM Survey s WHERE s.id = ?1")
    Integer findPaymentPerUserById(Long id);

    @Modifying
    @Transactional
    @Query("UPDATE Survey s SET s.isRejected = ?1 WHERE s.id = ?2")
    void rejectSurvey(Boolean isRejected, Long surveyId);
}
