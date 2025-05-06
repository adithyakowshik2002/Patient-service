package com.improveid.hms.patientservice.Repository;

import com.improveid.hms.patientservice.Entity.PatientEntity;
import com.improveid.hms.patientservice.Enums.BloodGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity,Long> {

    Optional<PatientEntity> findByEmail(String email);

    Optional<PatientEntity> findByAadhar(String aadhar);

    List<PatientEntity> findByBloodGroup(BloodGroup bloodGroup);

    Optional<PatientEntity> findById(Long patientId);
    //Optional<PatientEntity> findByPhoneNumber(String phoneNumber);
}
