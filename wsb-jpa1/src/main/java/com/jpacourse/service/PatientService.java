package com.jpacourse.service;

import java.util.List;

import com.jpacourse.dto.VisitTO;
import com.jpacourse.persistence.entity.PatientEntity;

public interface PatientService {
    PatientEntity findById(Long id);

    List<VisitTO> findAllVisitsByPatientId(Long id);
}
