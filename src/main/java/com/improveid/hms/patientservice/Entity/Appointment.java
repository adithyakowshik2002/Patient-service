package com.improveid.hms.patientservice.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.improveid.hms.patientservice.Enums.AppointmentStatus;
import com.improveid.hms.patientservice.Enums.AppointmentType;
import jakarta.persistence.*;
import lombok.*;
import com.improveid.hms.patientservice.Enums.RoomType;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name="appointments")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment {

    @Id
    @Column(name = "appointment_id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appointmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    @JsonBackReference
    private PatientEntity patient;

    @Column
    private Long scheduleId; //scheduleId from doctor service

    @Column(name = "appointment_date", nullable = false)
    private LocalDate appointmentDate;

    @Column(name = "time_slot",nullable = false)
    private LocalTime timeslot;


    @Column(nullable = false) // ADD THIS FIELD
    private Long doctorId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private AppointmentType appointmentType =AppointmentType.OP;//Default to OP

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
     private RoomType roomType=RoomType.NON_AC;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private AppointmentStatus status =AppointmentStatus.CANCELLED;

}