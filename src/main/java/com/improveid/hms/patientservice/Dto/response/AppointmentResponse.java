package com.improveid.hms.patientservice.Dto.response;

import com.improveid.hms.patientservice.Enums.AppointmentStatus;
import com.improveid.hms.patientservice.Enums.AppointmentType;
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
    private LocalDate appointmentDate;
    private LocalTime timeslot;
    private AppointmentType appointmentType; // Will be OP for normal bookings
    private AppointmentStatus status;

}
