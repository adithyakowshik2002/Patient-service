package com.improveid.hms.patientservice.Controller;


import com.improveid.hms.patientservice.Dto.response.PatientResponse;
import com.improveid.hms.patientservice.Dto.request.PatientRequest;
import com.improveid.hms.patientservice.Enums.BloodGroup;
import com.improveid.hms.patientservice.Service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @PostMapping
    public ResponseEntity<String> CreatePatientAndBookAppointment(@RequestBody PatientRequest patientRequest,@RequestParam Long doctorId,@RequestParam LocalDate slotDate,@RequestParam LocalTime slotStartTime)
    {
        patientService.registerPatientAndBookAppointment(patientRequest,doctorId,slotDate,slotStartTime);
        return new ResponseEntity<>("Patient registered and appointment booked successfully!",HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PatientResponse>> getALlPatients(){
        List<PatientResponse> Patients = patientService.getAllPatients();
        return new ResponseEntity<>(Patients,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponse> getPatientById(@PathVariable("id") Integer id)  {
       PatientResponse response = patientService.getPatientById(id);
        return new ResponseEntity<>(response,HttpStatus.OK);
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

    @PutMapping("/{id}")
    public ResponseEntity<PatientResponse> updatePatient(@PathVariable Integer id, @RequestBody PatientRequest patientRequest){
        PatientResponse updatePatient = patientService.updatePatient(id,patientRequest);
        return new ResponseEntity<>(updatePatient,HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable Integer id) {
        patientService.deletePatient(id);
        return new ResponseEntity<>("Deleted Successfully",HttpStatus.OK);
    }





}
