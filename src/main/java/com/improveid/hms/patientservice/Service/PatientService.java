package com.improveid.hms.patientservice.Service;


import com.improveid.hms.patientservice.Dto.response.PatientResponse;
import com.improveid.hms.patientservice.Dto.request.PatientRequest;
import com.improveid.hms.patientservice.Entity.Appointment;
import com.improveid.hms.patientservice.Entity.PatientEntity;
import com.improveid.hms.patientservice.Enums.BloodGroup;
import com.improveid.hms.patientservice.Exception.PatientNotFoundException;
import com.improveid.hms.patientservice.Exception.ResourceNotFoundException;
import com.improveid.hms.patientservice.Mapper.PatientMapper;
import com.improveid.hms.patientservice.Repository.PatientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;


@Service
@Slf4j
public class PatientService {

    @Autowired
    private   PatientRepository patientRepository;

    @Autowired
    private PatientMapper patientMapper;

    @Transactional
    public PatientResponse addPatient(PatientRequest patientRequest){
    log.info("Adding Product");
    PatientEntity patient = patientMapper.toEntity(patientRequest);
    if(patient.getAge() == null && patient.getDateOfBirth()!=null)
    {
        patient.setAge(Period.between(patient.getDateOfBirth(), LocalDate.now()).getYears());
    }

    if(patient.getAppointments() !=null)
    {
        for (Appointment appointment :patient.getAppointments()){
            appointment.setPatient(patient);
        }
    }

    PatientEntity savePatient = patientRepository.save(patient);
    log.info("Patient created");

        return patientMapper.toResponse(savePatient);
    }


    public List<PatientResponse> getAllPatients(){
        log.info("Fetching  all Patients");
        return patientRepository.findAll().stream().map(patientMapper::toResponse).toList();
    }

    public PatientResponse getPatientById(Integer id) throws PatientNotFoundException {
        log.info("fetching the patient of id "+id);

        return patientRepository.findById(id).map(patientMapper::toResponse).orElseThrow(()-> new PatientNotFoundException("Patient with ID " + id + " not found"));
    }


    public PatientResponse updatePatient(Integer id, PatientRequest patientRequest){

        PatientEntity existingPatient = patientRepository.findById(id).orElseThrow(()->new RuntimeException("Patient not found with ID: "+id));

        existingPatient.setFirstName(patientRequest.getFirstName());
        existingPatient.setLastName(patientRequest.getLastName());
        existingPatient.setGender(patientRequest.getGender());
        existingPatient.setDateOfBirth(patientRequest.getDateOfBirth());
        existingPatient.setAddress(patientRequest.getAadhar());
        existingPatient.setEmail(patientRequest.getEmail());
        existingPatient.setPhoneNumber(patientRequest.getPhoneNumber());
        existingPatient.setAddress(patientRequest.getAddress());
        existingPatient.setBloodGroup(patientRequest.getBloodGroup());
        existingPatient.AgeCalulate();

        PatientEntity savedPatient = patientRepository.save(existingPatient);

        return patientMapper.toResponse(savedPatient);

    }

    public void deletePatient(Integer id){
        PatientEntity deletingpatinet = patientRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Patient not found with ID: " + id));
        patientRepository.delete(deletingpatinet);
    }

    public PatientResponse getPatientByAadhar(String aadhar){
        PatientEntity patient = patientRepository.findByAadhar(aadhar).orElseThrow(()->new ResourceNotFoundException("Patient not found with Aadhaar "+aadhar));
        return patientMapper.toResponse(patient);
    }

    public PatientResponse getPatientByEmail(String email){
        PatientEntity patient = patientRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("Patient not found with email: "+email));
        return patientMapper.toResponse(patient);
    }

    public List<PatientResponse> getPatientsByBloodGroup(BloodGroup bloodGroup){
        List<PatientEntity> patients = patientRepository.findByBloodGroup(bloodGroup);
        if(patients.isEmpty()){
            throw new ResourceNotFoundException("No patients found with blood group: "+bloodGroup);
        }
        return patients.stream().map(patientMapper::toResponse).toList();
    }



}
