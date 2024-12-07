package com.jpacourse.service;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.persistence.dao.DoctorDao;
import com.jpacourse.persistence.dao.PatientDao;
import com.jpacourse.persistence.entity.DoctorEntity;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import com.jpacourse.service.impl.PatientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

    @Mock
    private PatientDao patientDao;

    @Mock
    private DoctorDao doctorDao;

    @InjectMocks
    private PatientServiceImpl patientService;

    private PatientEntity patientEntity;
    private DoctorEntity doctorEntity;
    private VisitEntity visitEntity;

    @BeforeEach
    public void setUp() {
        doctorEntity = new DoctorEntity();
        doctorEntity.setId(1L);
        doctorEntity.setFirstName("John");
        doctorEntity.setLastName("Doe");

        visitEntity = new VisitEntity();
        visitEntity.setId(1L);
        visitEntity.setDescription("Annual check-up");
        visitEntity.setTime(LocalDateTime.of(2023, 1, 1, 10, 0));
        visitEntity.setDoctor(doctorEntity);
        visitEntity.setMedicalTreatments(Collections.emptyList()); // Initialize the list

        patientEntity = new PatientEntity();
        patientEntity.setId(1L);
        patientEntity.setFirstName("Alice");
        patientEntity.setLastName("Johnson");
        patientEntity.setDateOfBirth(LocalDate.of(1980, 1, 1));
        patientEntity.setHeight(1.65);
        patientEntity.setVisits(Collections.singletonList(visitEntity));
    }

    @Test
    public void testDeletePatient() {
        when(patientDao.findOne(anyLong())).thenReturn(patientEntity);

        patientService.deletePatient(1L);

        verify(patientDao, times(1)).delete(patientEntity);
        verify(doctorDao, never()).delete(any(DoctorEntity.class));
    }

    @Test
    public void testFindPatientById() {
        when(patientDao.findOne(anyLong())).thenReturn(patientEntity);

        PatientTO patientTO = patientService.findById(1L);

        assertThat(patientTO).isNotNull();
        assertThat(patientTO.getFirstName()).isEqualTo("Alice");
        assertThat(patientTO.getLastName()).isEqualTo("Johnson");
        assertThat(patientTO.getHeight()).isEqualTo(1.65);
        assertThat(patientTO.getVisits()).hasSize(1);
        assertThat(patientTO.getVisits().get(0).getDescription()).isEqualTo("Annual check-up");
        assertThat(patientTO.getVisits().get(0).getDoctorFirstName()).isEqualTo("John");
        assertThat(patientTO.getVisits().get(0).getDoctorLastName()).isEqualTo("Doe");
    }
}
