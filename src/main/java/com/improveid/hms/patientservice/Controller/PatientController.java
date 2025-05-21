package com.improveid.hms.patientservice.Controller;


import com.improveid.hms.patientservice.Dto.request.AppointmentRequest;
import com.improveid.hms.patientservice.Dto.response.PatientResponse;
import com.improveid.hms.patientservice.Dto.request.PatientRequest;
import com.improveid.hms.patientservice.Enums.BloodGroup;
import com.improveid.hms.patientservice.Service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor

public class PatientController {

    private final PatientService patientService;

    @PostMapping("/register")
    public ResponseEntity<String> CreatePatientAndBookAppointment(@RequestBody PatientRequest patientRequest,
                                                                  @RequestParam Long doctorId,
                                                                  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate slotDate,
                                                                  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalTime slotStartTime,@RequestParam String otp)
    {
        patientService.registerPatientAndBookAppointment(patientRequest,doctorId,slotDate,slotStartTime,otp);
        return new ResponseEntity<>("Patient registered and appointment booked successfully!",HttpStatus.CREATED);
    }

    @PostMapping("/book-existing")
    public ResponseEntity<?> bookAppointmentForExistingPatient(@RequestParam(required = false) Long patientId,
                                                               @RequestParam(required = false) Long doctorId,
                                                               @RequestParam(required = false) LocalDate slotDate,
                                                               @RequestParam(required = false) LocalTime slotStartTime){

        List<String> missingParams = new ArrayList<>();

        if(patientId == null) missingParams.add("patientId");
        if(doctorId == null) missingParams.add("doctorId");
        if(slotDate == null) missingParams.add("slotDate");
        if(slotStartTime == null) missingParams.add("slotStartTime");

        if(!missingParams.isEmpty()){
            throw new IllegalArgumentException("Missing parameters: "+String.join(", ",missingParams));
        }
        patientService.bookForExistingPatient(patientId,doctorId,slotDate,slotStartTime);
        return new ResponseEntity<>("Existing patient booked successfully!",HttpStatus.OK);
    }
    @GetMapping("getallpatients")
    public ResponseEntity<List<PatientResponse>> getALlPatients(){
        List<PatientResponse> Patients = patientService.getAllPatients();
        return new ResponseEntity<>(Patients,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponse> getPatientById(@PathVariable("id") Long id)  {
       PatientResponse response = patientService.getPatientById(id);
        return new ResponseEntity<>(response,HttpStatus.OK);
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
    public ResponseEntity<PatientResponse> updatePatient(@PathVariable Long id, @RequestBody PatientRequest patientRequest){
        PatientResponse updatePatient = patientService.updatePatient(id,patientRequest);
        return new ResponseEntity<>(updatePatient,HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return new ResponseEntity<>("Deleted Successfully",HttpStatus.OK);
    }

    @GetMapping("/aadhar/{aadhar}")
    public ResponseEntity<PatientResponse> getPatientByAadhar(@PathVariable String aadhar){
        PatientResponse response =patientService.getPatientByAadhar(aadhar);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
