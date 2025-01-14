package com.jpacourse.service;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.dto.VisitTO;
import com.jpacourse.persistence.dao.DoctorDao;
import com.jpacourse.persistence.dao.PatientDao;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.service.impl.PatientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = { Service.class,
        Repository.class }))
public class PatientServiceTest {

    @Autowired
    private PatientServiceImpl patientService;

    @BeforeEach
    public void setUp() {
        // Ensure the data.sql file is loaded before each test
        // entityManager.getEntityManager().createNativeQuery("RUNSCRIPT FROM
        // 'classpath:data.sql'").executeUpdate();
    }

    @Test
    public void testFindPatientById() {
        PatientEntity patientEntity = patientService.findById(1L);
        assertThat(patientEntity).isNotNull();

        PatientEntity patient = patientService.findById(1L);

        assertThat(patient).isNotNull();
        assertThat(patient.getFirstName()).isEqualTo("Alice");
        assertThat(patient.getLastName()).isEqualTo("Johnson");
        assertThat(patient.getHeight()).isEqualTo(1.65);
        assertThat(patient.getVisits()).hasSize(1);
        assertThat(patient.getVisits().get(0).getDescription()).isEqualTo("Annual check-up");
        assertThat(patient.getVisits().get(0).getDoctor().getFirstName()).isEqualTo("John");
        assertThat(patient.getVisits().get(0).getDoctor().getLastName()).isEqualTo("Doe");
    }

    @Test
    public void testDeletePatient() {
        PatientEntity patientEntity = patientService.findById(1L);
        assertThat(patientEntity).isNotNull();

        patientService.deletePatient(1L);

        PatientEntity deletedPatient = patientService.findById(1L);
        assertThat(deletedPatient).isNull();
    }

    @Test
    public void testFindAllVisitsByPatientId() {
        Long patientId = 1L;
        List<VisitTO> visits = patientService.findAllVisitsByPatientId(patientId);
        assertThat(visits.size()).isEqualTo(1);
        assertThat(visits.get(0).getDescription()).isEqualTo("Annual check-up");
        assertThat(visits.get(0).getDoctorFirstName()).isEqualTo("John");
        assertThat(visits.get(0).getDoctorLastName()).isEqualTo("Doe");
    }
}
