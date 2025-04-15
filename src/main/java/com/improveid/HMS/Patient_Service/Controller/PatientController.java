package com.improveid.HMS.Patient_Service.Controller;


import com.improveid.HMS.Patient_Service.Dto.response.PatientResponse;
import com.improveid.HMS.Patient_Service.Dto.request.PatientRequest;
import com.improveid.HMS.Patient_Service.Enums.BloodGroup;
import com.improveid.HMS.Patient_Service.Exception.PatientNotFoundException;
import com.improveid.HMS.Patient_Service.Service.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/patients")
public class PatientController {


    @Autowired
    private  PatientService patientService;

    @PostMapping
    public ResponseEntity<PatientResponse> addPatient(@Valid @RequestBody PatientRequest patientRequest){

       PatientResponse addPatient = patientService.addPatient(patientRequest);
        return new ResponseEntity<>(addPatient,HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PatientResponse>> getALlPatients(){
        List<PatientResponse> Patients = patientService.getAllPatients();
        return new ResponseEntity<>(Patients,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponse> getPatientById(@PathVariable Integer id) throws PatientNotFoundException {
       PatientResponse response = patientService.getPatientById(id);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientResponse> updatePatient(@PathVariable Integer id, @RequestBody PatientRequest patientRequest){
        PatientResponse updatePatient = patientService.updatePatient(id,patientRequest);
        return new ResponseEntity<>(updatePatient,HttpStatus.OK);
    }

    @GetMapping("/aadhar/{aadhar}")
    public ResponseEntity<PatientResponse> getPatientByAadhar(@PathVariable String aadhar){
        PatientResponse patientResponse = patientService.getPatientByAadhar(aadhar);
        return new ResponseEntity<>(patientResponse,HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<PatientResponse> getPatientByEmail(@PathVariable String email){
        PatientResponse patientResponse = patientService.getPatientByEmail(email);
        return new ResponseEntity<>(patientResponse,HttpStatus.OK);
    }

    @GetMapping("/bloodgroup/{bloodGroup}")
    public ResponseEntity<List<PatientResponse>> getPatientsByBloodGroup(@PathVariable BloodGroup bloodGroup){
        List<PatientResponse> patients = patientService.getPatientsByBloodGroup(bloodGroup);

        return new ResponseEntity<>(patients,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable Integer id) {
        patientService.deletePatient(id);
        return new ResponseEntity<>("Deleted Successfully",HttpStatus.OK);
    }





}
