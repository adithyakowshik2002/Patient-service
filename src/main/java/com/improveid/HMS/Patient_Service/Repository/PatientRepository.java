package com.improveid.HMS.Patient_Service.Repository;

import com.improveid.HMS.Patient_Service.Entity.PatientEntity;
import com.improveid.HMS.Patient_Service.Enums.BloodGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity,Integer> {

    Optional<PatientEntity> findByEmail(String email);

    Optional<PatientEntity> findByAadhar(String aadhar);

    List<PatientEntity> findByBloodGroup(BloodGroup bloodGroup);
}
