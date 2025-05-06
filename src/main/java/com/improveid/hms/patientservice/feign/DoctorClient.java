package com.improveid.hms.patientservice.feign;

import com.improveid.hms.patientservice.Dto.otherService.DoctorDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;

@FeignClient(name = "DOCTOR-SERVICE")
public interface DoctorClient {

    @GetMapping("/api/doctors/find-schedule-id")
    Long findScheduleId(@RequestParam Long doctorId,
                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime slotTime);

    @PostMapping("/api/doctors/book-slot")
    void bookSlot(@RequestBody SlotBookedDTO request);

    @GetMapping("api/doctors/getbyid/{id}")
    DoctorDto getDoctorById(@PathVariable Long id);
}
