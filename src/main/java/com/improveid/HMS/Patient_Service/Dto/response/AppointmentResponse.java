package com.improveid.HMS.Patient_Service.Dto.response;

import com.improveid.HMS.Patient_Service.Enums.AppointmentStatus;
import com.improveid.HMS.Patient_Service.Enums.AppointmentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentResponse {

    private Long appointmentId;
    private Long patientId;
    private Long doctorId;
    private LocalDate appointmentDate;
    private LocalTime timeslot;
    private AppointmentType appointmentType; // Will be OP for normal bookings
    private AppointmentStatus status;

}
