package com.improveid.hms.patientservice.Dto.request;

import com.improveid.hms.patientservice.Enums.AppointmentStatus;
import com.improveid.hms.patientservice.Enums.AppointmentType;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
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
public class AppointmentRequest {



    private Long appointmentId;


    // ADD THIS FIELD
    private Long doctorId;

    private Long patientId;

    private LocalDate appointmentDate;

    private LocalTime timeslot;

    private AppointmentType appointmentType;  // OP or IP

    private AppointmentStatus status;

}
