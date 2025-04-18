package com.improveid.HMS.Patient_Service.Dto.request;

import com.improveid.HMS.Patient_Service.Enums.AppointmentStatus;
import com.improveid.HMS.Patient_Service.Enums.AppointmentType;
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
    @NotNull(message = "Doctor ID cannot be null")
    private Long doctorId;

    @NotNull(message = "Patient ID cannot be null")
    private Long patientId;

    @NotNull(message = "Appointment Date cannot be null")
    @FutureOrPresent(message = "Appointment Date must be today or in the future")
    private LocalDate appointmentDate;

    @NotNull(message = "Time slot cannot be null")
    private LocalTime timeslot;

    @NotNull(message = "Appointment Type cannot be null")
    private AppointmentType appointmentType;  // OP or IP

    @NotNull(message = "Status cannot be null")
    private AppointmentStatus status;




}
