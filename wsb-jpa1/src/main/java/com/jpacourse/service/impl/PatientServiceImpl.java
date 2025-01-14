package com.jpacourse.service.impl;

import com.jpacourse.dto.VisitTO;
import com.jpacourse.mapper.PatientMapper;
import com.jpacourse.mapper.VisitMapper;
import com.jpacourse.persistence.dao.PatientDao;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import com.jpacourse.service.PatientService;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PatientServiceImpl implements PatientService {

    private final PatientDao patientDao;

    @Autowired
    public PatientServiceImpl(PatientDao patientDao) {
        this.patientDao = patientDao;
    }

    @Override
    public PatientEntity findById(Long id) {
        return patientDao.findOne(id);
    }

    public void deletePatient(Long id) {
        PatientEntity patient = patientDao.findOne(id);
        if (patient != null) {
            patientDao.delete(patient);
        }
    }

    @Override
    public List<VisitTO> findAllVisitsByPatientId(Long id) {
        PatientEntity patient = patientDao.findOne(id);
        if (patient != null) {
            return patient.getVisits().stream()
                    .map(VisitMapper::mapToTO)
                    .collect(Collectors.toList());
        }
        return null;
    }
}