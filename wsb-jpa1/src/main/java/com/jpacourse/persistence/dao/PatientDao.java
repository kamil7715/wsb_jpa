package com.jpacourse.persistence.dao;

import java.time.LocalDateTime;
import java.util.List;

import com.jpacourse.persistence.entity.PatientEntity;

public interface PatientDao extends Dao<PatientEntity, Long> {
    void addVisitToPatient(Long patientId, Long doctorId, LocalDateTime visitTime, String description);

    public List<PatientEntity> findPatientsByLastName(String lastName);

    List<PatientEntity> findPatientsWithMoreThanXVisits(int visitCount);

    List<PatientEntity> findPatientsWithHeightGreaterThan(double height);
}
