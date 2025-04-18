package com.improveid.HMS.Patient_Service.Service;
import com.improveid.HMS.Patient_Service.Dto.request.AppointmentRequest;
import com.improveid.HMS.Patient_Service.Dto.response.AppointmentResponse;
import com.improveid.HMS.Patient_Service.Entity.Appointment;
import com.improveid.HMS.Patient_Service.Enums.AppointmentStatus;
import com.improveid.HMS.Patient_Service.Exception.CustomException;
import com.improveid.HMS.Patient_Service.Mapper.AppointmentMapper;
import com.improveid.HMS.Patient_Service.Repository.AppointmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import java.util.stream.Collectors;

@Slf4j
@Service
public class AppointmentServiceImpl implements AppointmentService{



    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private AppointmentMapper appointmentMapper;

    @Override
    public AppointmentResponse bookAppointment(AppointmentRequest request){
        log.info("Booking appointment for doctorId: {}, patientId: {}, date: {}, time: {}",
                request.getDoctorId(), request.getPatientId(),
                request.getAppointmentDate(), request.getTimeslot());

        boolean isSlotBooked = appointmentRepository.existsByDoctorIdAndAppointmentDateAndTimeSlot(request.getDoctorId(),request.getAppointmentDate(),request.getTimeslot());

        if (request.getDoctorId() == null || request.getPatientId() == null || request.getAppointmentDate() == null || request.getTimeslot() == null) {
            throw new CustomException("All fields are required to book an appointment", HttpStatus.BAD_REQUEST);
        }
        if(isSlotBooked){
        log.info("Doctor is already booked at the requested slot.");
        throw new CustomException("This time slot is already booked for the  doctor",HttpStatus.CONFLICT);
        }
        Appointment appointment = appointmentMapper.toEntity(request);
        log.info("Appointment booked with ID: {}", appointment.getAppointmentId());
        if(appointment.getStatus()==null)
       {
           appointment.setStatus(AppointmentStatus.BOOKED);
       }

        appointmentRepository.save(appointment);

        return appointmentMapper.toResponse(appointment);
    }

    // fetch the doctor on a specific date
    public List<AppointmentResponse> getAppointmentsFromDoctorOnDate(Long doctorId, LocalDate date){

        log.info("Fetching appointments for doctorId: {} on {}", doctorId, date);

        if(doctorId == null||date ==null)
        {
            throw new CustomException("Doctor ID and date are requested",HttpStatus.BAD_REQUEST);
        }
        List<Appointment> appointments = appointmentRepository.findByDoctorIdAndAppointmentDate(doctorId,date);

        return appointments.stream().map(appointmentMapper::toResponse).collect(Collectors.toList());
    }

    //  it will fetch the patient
    @Override
    public List<AppointmentResponse> getAppointmentsForPatient(Long patientId) {
        log.info("Fetching appointments for patientId: {}", patientId);
        if(patientId == null|| patientId <=0){
            throw new CustomException("Invalid patient ID", HttpStatus.BAD_REQUEST);
        }

        List<Appointment> appointments = appointmentRepository.findByPatientId(patientId);

        if(appointments == null||appointments.isEmpty()){
            throw new CustomException("No appointments found for patient ID:"+patientId,HttpStatus.NOT_FOUND);

        }


        return appointmentMapper.toResponse(appointments);
    }

    @Override
    public List<AppointmentResponse> getAppointmentsByStatus(String status) {
        AppointmentStatus appointmentStatus;

        try{
            appointmentStatus = AppointmentStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomException("Invalid appointment status: "+status,HttpStatus.BAD_REQUEST);
        }

        List<Appointment> appointments = appointmentRepository.findByStatus(appointmentStatus);

        return appointments.stream().map(appointmentMapper::toResponse).toList();
    }

    @Override
    public AppointmentResponse updateAppointment(Long id, AppointmentRequest request) {

       if(request == null || id == null)
       {
           throw new CustomException("Appointment ID and details are required",HttpStatus.BAD_REQUEST);
       }
        Appointment existing = appointmentRepository.findById(id)
                .orElseThrow(() -> new CustomException("Appointment not found",HttpStatus.NOT_FOUND));
        request.setAppointmentId(existing.getAppointmentId());

        Appointment updateEntity = appointmentMapper.toEntity(request);

        updateEntity.setAppointmentId(existing.getAppointmentId());

        Appointment savedAppointment  = appointmentRepository.save(updateEntity);
        return appointmentMapper.toResponse(savedAppointment);
    }

    @Override
    public void cancelAppointment(Long id) {
    Appointment appointment = appointmentRepository.findById(id).orElseThrow(()->new CustomException("Appointment not found with id: ",HttpStatus.NOT_FOUND));

    appointment.setStatus(AppointmentStatus.CANCELLED);
    appointmentRepository.save(appointment);
    }

    @Override
    public AppointmentResponse getAppointmentById(Long id) {

    Appointment appointment =  appointmentRepository.findById(id).orElseThrow(()->new CustomException("Appointment not found",HttpStatus.NOT_FOUND));
        return appointmentMapper.toResponse(appointment);

    }








}
