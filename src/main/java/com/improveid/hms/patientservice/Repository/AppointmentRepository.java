package com.improveid.hms.patientservice.Repository;


import com.improveid.hms.patientservice.Entity.Appointment;
import com.improveid.hms.patientservice.Enums.AppointmentStatus;
import com.improveid.hms.patientservice.Enums.AppointmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // To fetch all appointments by the patientId
    //senatio ENT appointmet at 11 AM adn Cardiologist appointemet at 2pm 12th
    // so all that two appointmet linked to the same patientId.
    List<Appointment> findByPatientId(Long patientId);

   Appointment findByPatientIdAndAppointmentDateAndTimeslot(Long patientId,LocalDate date,LocalTime time);
    List<Appointment> findByStatus(AppointmentStatus status);

    List<Appointment> findByDoctorId(Long doctorId);

}
