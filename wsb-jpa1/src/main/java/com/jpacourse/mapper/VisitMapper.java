package com.jpacourse.mapper;

import com.jpacourse.dto.VisitTO;
import com.jpacourse.persistence.entity.VisitEntity;

import java.util.stream.Collectors;

public final class VisitMapper {

    private VisitMapper() {
        // Private constructor to prevent instantiation
    }

    public static VisitTO mapToTO(final VisitEntity visitEntity) {
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

    public static VisitEntity mapToEntity(final VisitTO visitTO) {
        if (visitTO == null) {
            return null;
        }
        VisitEntity visitEntity = new VisitEntity();
        visitEntity.setId(visitTO.getId());
        visitEntity.setDescription(visitTO.getDescription());
        visitEntity.setTime(visitTO.getTime());
        // Assuming you have methods to find DoctorEntity and PatientEntity by their
        // names or IDs
        // visitEntity.setDoctor(findDoctorByName(visitTO.getDoctorFirstName(),
        // visitTO.getDoctorLastName()));
        // visitEntity.setPatient(findPatientById(visitTO.getPatientId()));
        // visitEntity.setMedicalTreatments(findMedicalTreatmentsByTypes(visitTO.getTreatmentTypes()));
        return visitEntity;
    }
}