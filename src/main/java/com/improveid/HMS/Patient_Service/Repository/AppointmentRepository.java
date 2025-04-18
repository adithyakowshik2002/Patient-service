package com.improveid.HMS.Patient_Service.Repository;


import com.improveid.HMS.Patient_Service.Entity.Appointment;
import com.improveid.HMS.Patient_Service.Enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // To fetch all appointments for a specific doctor on a specific date
    List<Appointment> findByDoctorIdAndAppointmentDate(Long doctorId, LocalDate appointmentDate);

    // To fetch all appointments by the patientId
    //senatio ENT appointmet at 11 AM adn Cardiologist appointemet at 2pm 12th
    // so all that two appointmet linked to the same patientId.
    List<Appointment> findByPatientId(Long patientId);

    List<Appointment> findByStatus(AppointmentStatus status);

    boolean existsByDoctorIdAndAppointmentDateAndTimeSlot(Long doctorId,LocalDate date,LocalTime time);

}
