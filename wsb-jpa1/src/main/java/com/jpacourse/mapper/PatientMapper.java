package com.jpacourse.mapper;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.dto.VisitTO;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;

import java.util.stream.Collectors;

public final class PatientMapper {

    public static PatientTO mapToTO(final PatientEntity patientEntity) {
        if (patientEntity == null) {
            return null;
        }
        PatientTO patientTO = new PatientTO();
        patientTO.setId(patientEntity.getId());
        patientTO.setFirstName(patientEntity.getFirstName());
        patientTO.setLastName(patientEntity.getLastName());
        patientTO.setTelephoneNumber(patientEntity.getTelephoneNumber());
        patientTO.setEmail(patientEntity.getEmail());
        patientTO.setPatientNumber(patientEntity.getPatientNumber());
        patientTO.setDateOfBirth(patientEntity.getDateOfBirth());
        patientTO.setAddress(AddressMapper.mapToTO(patientEntity.getAddress()));
        patientTO.setVisits(
                patientEntity.getVisits().stream().map(PatientMapper::mapVisitToTO).collect(Collectors.toList()));
        patientTO.setHeight(patientEntity.getHeight()); // Example of a non-String field
        return patientTO;
    }

    private static VisitTO mapVisitToTO(final VisitEntity visitEntity) {
        if (visitEntity == null) {
            return null;
        }
        VisitTO visitTO = new VisitTO();
        visitTO.setId(visitEntity.getId());
        visitTO.setDescription(visitEntity.getDescription());
        visitTO.setTime(visitEntity.getTime());
        visitTO.setDoctorFirstName(visitEntity.getDoctor().getFirstName());
        visitTO.setDoctorLastName(visitEntity.getDoctor().getLastName());
        visitTO.setTreatmentTypes(
                visitEntity.getMedicalTreatments().stream().map(t -> t.getType().name()).collect(Collectors.toList()));
        return visitTO;
    }

    public static PatientEntity mapToEntity(final PatientTO patientTO) {
        if (patientTO == null) {
            return null;
        }
        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setId(patientTO.getId());
        patientEntity.setFirstName(patientTO.getFirstName());
        patientEntity.setLastName(patientTO.getLastName());
        patientEntity.setTelephoneNumber(patientTO.getTelephoneNumber());
        patientEntity.setEmail(patientTO.getEmail());
        patientEntity.setPatientNumber(patientTO.getPatientNumber());
        patientEntity.setDateOfBirth(patientTO.getDateOfBirth());
        patientEntity.setAddress(AddressMapper.mapToEntity(patientTO.getAddress()));
        patientEntity.setHeight(patientTO.getHeight()); // Example of a non-String field
        return patientEntity;
    }
}
