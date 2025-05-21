package com.improveid.hms.patientservice.Service;

import com.improveid.hms.patientservice.Dto.request.AppointmentRequest;
import com.improveid.hms.patientservice.Dto.response.AppointmentResponse;
import com.improveid.hms.patientservice.Dto.response.AppointmentsDto;
import com.improveid.hms.patientservice.Entity.Appointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentService {
    AppointmentResponse updateAppointment(Long id, AppointmentRequest appointment);
    void cancelAppointment(Long id);
    AppointmentResponse getAppointmentById(Long id);
    List<AppointmentResponse> getAppointmentsForPatient(Long patientId);
    List<AppointmentResponse> getAppointmentsByStatus(String status);
    AppointmentResponse convertToIP(Long appointmentId);
    Long getPatientIdByAppointmentId(Long appointmentId);
    String getRoomTypeByAppointmentId(Long appointmentId);
    List<AppointmentsDto> getAppointmentsByDoctorId(Long doctorId);
    Appointment getAppointmentByPatientIdAndDateAndTime(Long patientId, LocalDate date, LocalTime time);
}
