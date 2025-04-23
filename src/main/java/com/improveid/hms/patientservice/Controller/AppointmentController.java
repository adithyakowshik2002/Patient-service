package com.improveid.hms.patientservice.Controller;

import com.improveid.hms.patientservice.Dto.request.AppointmentRequest;
import com.improveid.hms.patientservice.Dto.request.PatientRequest;
import com.improveid.hms.patientservice.Dto.response.AppointmentResponse;
import com.improveid.hms.patientservice.Dto.response.PatientResponse;
import com.improveid.hms.patientservice.Service.AppointmentServiceImpl;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@Slf4j
public class AppointmentController {

    @Autowired
    private AppointmentServiceImpl appointmentService;



    @PostMapping("/book")
    public ResponseEntity<AppointmentResponse> book(@Valid @RequestBody AppointmentRequest request) {
        log.info("Booking appointment for patientId={}", request.getPatientId());
        AppointmentResponse appointment = appointmentService.bookAppointment(request);
        return new ResponseEntity<>(appointment, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponse> getAppointmentById(@PathVariable Long id) {

        AppointmentResponse appointment = appointmentService.getAppointmentById(id);
        return new ResponseEntity<>(appointment,HttpStatus.OK);
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<AppointmentResponse>> getAppointmentsForPatient(@PathVariable Long patientId) {
        log.info("Fetching appointments for patient {}", patientId);
        List<AppointmentResponse> appointments = appointmentService.getAppointmentsForPatient(patientId);
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @PutMapping("/reschedule/{appointmentId}")
    public ResponseEntity<AppointmentResponse> rescheduleAppointment(@PathVariable Long appointmentId,@Valid @RequestBody AppointmentRequest request){
        AppointmentResponse updated = appointmentService.updateAppointment(appointmentId,request);
        return new ResponseEntity<>(updated,HttpStatus.OK);
    }


    @DeleteMapping
    public ResponseEntity<Void> cancel(@PathVariable Long id){
        appointmentService.cancelAppointment(id);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/{appointmentId}/converttoip")
    public ResponseEntity<String> convertToIp(@PathVariable Long appointmentId, @RequestBody AppointmentRequest request) {
        AppointmentResponse appointment = appointmentService.convertToIP(appointmentId,request);
        return ResponseEntity.ok("Appointment Converted to IP successfully");
    }




}
