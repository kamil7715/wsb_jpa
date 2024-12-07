package com.jpacourse.persistence.dao;

import com.jpacourse.persistence.entity.DoctorEntity;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import com.jpacourse.persistence.enums.Specialization;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Repository.class))
public class PatientDaoTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PatientDao patientDao;

    private PatientEntity patient;
    private DoctorEntity doctor;

    @BeforeEach
    public void setUp() {
        doctor = new DoctorEntity();
        doctor.setFirstName("John");
        doctor.setLastName("Doe");
        doctor.setDoctorNumber("D001"); // Set the doctor number
        doctor.setSpecialization(Specialization.SURGEON); // Set the specialization using the enum
        doctor.setTelephoneNumber("555-1234"); // Set the telephone number
        doctor.setEmail("john.doe@example.com"); // Set the email
        entityManager.persist(doctor);

        patient = new PatientEntity();
        patient.setFirstName("Alice");
        patient.setLastName("Johnson");
        patient.setTelephoneNumber("555-8765"); // Set the telephone number
        patient.setEmail("alice.johnson@example.com"); // Set the email
        patient.setDateOfBirth(LocalDate.of(1980, 1, 1)); // Set the date of birth
        patient.setHeight(1.65); // Set the height
        patient.setPatientNumber("P001"); // Set the patient number
        patient.setVisits(new ArrayList<>()); // Initialize the visits list
        entityManager.persist(patient);
    }

    @Test
    public void testAddVisitToPatient() {
        LocalDateTime visitTime = LocalDateTime.of(2023, 1, 1, 10, 0);
        String description = "Annual check-up";

        patientDao.addVisitToPatient(patient.getId(), doctor.getId(), visitTime, description);

        PatientEntity updatedPatient = entityManager.find(PatientEntity.class, patient.getId());
        assertThat(updatedPatient.getVisits()).hasSize(1);

        VisitEntity visit = updatedPatient.getVisits().get(0);
        assertThat(visit.getDescription()).isEqualTo(description);
        assertThat(visit.getTime()).isEqualTo(visitTime);
        assertThat(visit.getDoctor().getId()).isEqualTo(doctor.getId());
    }
}
