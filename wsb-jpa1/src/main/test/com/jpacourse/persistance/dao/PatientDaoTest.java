package com.jpacourse.persistance.dao;

import com.jpacourse.persistence.dao.PatientDao;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Repository.class))
public class PatientDaoTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PatientDao patientDao;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @BeforeEach
    public void setUp() {
        // Ensure the data.sql file is loaded before each test
        // entityManager.getEntityManager().createNativeQuery("RUNSCRIPT FROM
        // 'classpath:data.sql'").executeUpdate();
    }

    @Test
    public void testAddVisitToPatient() {
        PatientEntity patient = entityManager.find(PatientEntity.class, 1L);
        LocalDateTime visitTime = LocalDateTime.of(2023, 1, 1, 10, 0);
        String description = "Annual check-up";
        // visits before adding a new visit number of store
        int visitsBefore = patient.getVisits().size();

        patientDao.addVisitToPatient(patient.getId(), 1L, visitTime, description);

        PatientEntity updatedPatient = entityManager.find(PatientEntity.class, patient.getId());
        assertThat(updatedPatient.getVisits()).hasSize(visitsBefore + 1);

        VisitEntity visit = updatedPatient.getVisits().get(0);
        assertThat(visit.getDescription()).isEqualTo(description);
        assertThat(visit.getTime()).isEqualTo(visitTime);
        assertThat(visit.getDoctor().getId()).isEqualTo(1L);
    }

    @Test
    public void testFindPatientsByLastName() {
        List<PatientEntity> patients = patientDao.findPatientsByLastName("Johnson");
        assertEquals(1, patients.size());
        assertEquals("Johnson", patients.get(0).getLastName());
    }

    @Test
    public void testFindPatientsWithMoreThanXVisits() {
        int visitCount = 0;
        List<PatientEntity> patients = patientDao.findPatientsWithMoreThanXVisits(visitCount);
        assertThat(patients).isNotEmpty();
        assertThat(patients.get(0).getVisits().size()).isGreaterThan(visitCount);
    }

    @Test
    public void testFindPatientsWithHeightGreaterThan() {
        double height = 1.70;
        List<PatientEntity> patients = patientDao.findPatientsWithHeightGreaterThan(height);
        assertThat(patients).isNotEmpty();
        assertThat(patients.get(0).getHeight()).isGreaterThan(height);
    }

    @Test
    public void testOptimisticLockingOnPatientWithThreads() throws InterruptedException {
        Long patientId = 2L;
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<Throwable> exceptionReference = new AtomicReference<>();

        Thread thread1 = new Thread(() -> {
            PatientEntity patient1 = patientDao.findOne(patientId);
            try {
                latch.await();
                patient1.setTelephoneNumber("111-1111");
                patientDao.update(patient1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (Throwable t) {
                exceptionReference.set(t);
            }
        });

        Thread thread2 = new Thread(() -> {
            PatientEntity patient2 = patientDao.findOne(patientId);
            patient2.setTelephoneNumber("222-2222");
            patientDao.update(patient2);
            latch.countDown();
        });

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        assertThat(exceptionReference.get()).isInstanceOf(javax.persistence.OptimisticLockException.class);
    }

}
