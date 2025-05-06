package com.improveid.hms.patientservice.Service;


import com.improveid.hms.patientservice.Dto.otherService.DoctorDto;
import com.improveid.hms.patientservice.Dto.otherService.MailDtoClass;
import com.improveid.hms.patientservice.Dto.response.PatientResponse;
import com.improveid.hms.patientservice.Dto.request.PatientRequest;
import com.improveid.hms.patientservice.Entity.Appointment;
import com.improveid.hms.patientservice.Entity.PatientEntity;
import com.improveid.hms.patientservice.Enums.AppointmentStatus;
import com.improveid.hms.patientservice.Enums.AppointmentType;
import com.improveid.hms.patientservice.Enums.BloodGroup;
import com.improveid.hms.patientservice.Exception.PatientNotFoundException;
import com.improveid.hms.patientservice.Exception.ResourceNotFoundException;
import com.improveid.hms.patientservice.Mapper.PatientMapper;
import com.improveid.hms.patientservice.Repository.AppointmentRepository;
import com.improveid.hms.patientservice.Repository.PatientRepository;
import com.improveid.hms.patientservice.feign.DoctorClient;
import com.improveid.hms.patientservice.feign.MailServiceClient;
import com.improveid.hms.patientservice.feign.SlotBookedDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@Service
@Slf4j
public class PatientService {

    @Autowired
    private   PatientRepository patientRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorClient doctorClient;

    @Autowired
    private PatientMapper patientMapper;

    @Autowired
    private MailServiceClient mailServiceClient;

    public void registerPatientAndBookAppointment(PatientRequest request, Long doctorId, LocalDate slotDate, LocalTime slotStartTime) {

        Long scheduleId = doctorClient.findScheduleId(doctorId, slotDate, slotStartTime);

        if (scheduleId == null) {
            throw new IllegalArgumentException("No schedule found for the given doctor, date, and time.");
        }

        PatientEntity patient = patientMapper.toEntity(request);
        patient = patientRepository.save(patient);

        Appointment appointment = Appointment.builder()
                .patient(patient)
                .scheduleId(scheduleId)
                .appointmentDate(slotDate)
                .timeslot(slotStartTime)  //start time
                .appointmentType(AppointmentType.OP)
                .status(AppointmentStatus.BOOKED)
                .doctorId(doctorId)
                .build();

        appointmentRepository.save(appointment);


        // 4. Book Slot in Doctor Service
        SlotBookedDTO slotRequest = SlotBookedDTO.builder()
                .scheduleId(scheduleId)
                .patientId(patient.getId())
                .slotDate(String.valueOf(slotDate))
                .slotStartTime(String.valueOf(slotStartTime))
                .slotEndTime(String.valueOf(slotStartTime.plusMinutes(15)))
                .status("BOOKED")
                .build();

        doctorClient.bookSlot(slotRequest);

        DoctorDto doctor = doctorClient.getDoctorById(doctorId);



        MailDtoClass mail = MailDtoClass.builder()
                .fullName(patient.getFirstName()+patient.getLastName())
                .doctorName(doctor.getName())
                .bookedDate(appointment.getAppointmentDate())
                .slotTime(appointment.getTimeslot())
                .mailId(patient.getEmail())
                .build();

        mailServiceClient.sendEmail(mail);


    }

    public void bookForExistingPatient(Long patientId,Long doctorId,LocalDate slotDate,LocalTime slotStartingTime) {

        Long scheduleId = doctorClient.findScheduleId(doctorId,slotDate,slotStartingTime);
        if (scheduleId == null) {
            throw new IllegalArgumentException("No schedule found for the given doctor, date, and time.");
        }

        PatientEntity patient = patientRepository.findById(patientId).orElseThrow(()->new PatientNotFoundException("Patient not found."));
        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setScheduleId(scheduleId);
        appointment.setDoctorId(doctorId);
        appointment.setAppointmentDate(slotDate);
        appointment.setTimeslot(slotStartingTime);
        appointment.setAppointmentType(AppointmentType.OP);
        appointment.setStatus(AppointmentStatus.BOOKED);

        appointmentRepository.save(appointment);

        // 4. Book Slot in Doctor Service
        SlotBookedDTO slotRequest = SlotBookedDTO.builder()
                .scheduleId(scheduleId)
                .patientId(patient.getId())
                .slotDate(String.valueOf(slotDate))
                .slotStartTime(String.valueOf(slotStartingTime))
                .slotEndTime(String.valueOf(slotStartingTime.plusMinutes(15)))
                .status("BOOKED")
                .build();

        doctorClient.bookSlot(slotRequest);

        DoctorDto doctor = doctorClient.getDoctorById(doctorId);


        MailDtoClass mail = MailDtoClass.builder()
                .fullName(patient.getFirstName()+patient.getLastName())
                .doctorName(doctor.getName())
                .bookedDate(appointment.getAppointmentDate())
                .slotTime(appointment.getTimeslot())
                .mailId(patient.getEmail())
                .build();
        mailServiceClient.sendEmail(mail);

    }



    public List<PatientResponse> getAllPatients(){
        log.info("Fetching  all Patients");
        return patientRepository.findAll().stream().map(patientMapper::toResponse).toList();
    }

    public PatientResponse getPatientById(Long id) throws PatientNotFoundException {
        log.info("fetching the patient of id "+id);

        return patientRepository.findById(id).map(patientMapper::toResponse).orElseThrow(()-> new PatientNotFoundException("Patient with ID " + id + " not found"));
    }


    public PatientResponse updatePatient(Long id, PatientRequest patientRequest){

        PatientEntity existingPatient = patientRepository.findById(id).orElseThrow(()->new RuntimeException("Patient not found with ID: "+id));

        existingPatient.setFirstName(patientRequest.getFirstName());
        existingPatient.setLastName(patientRequest.getLastName());
        existingPatient.setGender(patientRequest.getGender());
        existingPatient.setDateOfBirth(patientRequest.getDateOfBirth());
        existingPatient.setAddress(patientRequest.getAadhar());
        existingPatient.setEmail(patientRequest.getEmail());
        existingPatient.setPhoneNumber(patientRequest.getPhoneNumber());
        existingPatient.setAddress(patientRequest.getAddress());
        existingPatient.setBloodGroup(patientRequest.getBloodGroup());
        existingPatient.AgeCalulate();

        PatientEntity savedPatient = patientRepository.save(existingPatient);

        return patientMapper.toResponse(savedPatient);

    }

    public void deletePatient(Long id){
        PatientEntity deletingpatinet = patientRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Patient not found with ID: " + id));
        patientRepository.delete(deletingpatinet);
    }

    public PatientResponse getPatientByAadhar(String aadhar){
        PatientEntity patient = patientRepository.findByAadhar(aadhar).orElseThrow(()->new ResourceNotFoundException("Patient not found with Aadhaar "+aadhar));
        return patientMapper.toResponse(patient);
    }

    public PatientResponse getPatientByEmail(String email){
        PatientEntity patient = patientRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("Patient not found with email: "+email));
        return patientMapper.toResponse(patient);
    }

    public List<PatientResponse> getPatientsByBloodGroup(BloodGroup bloodGroup){
        List<PatientEntity> patients = patientRepository.findByBloodGroup(bloodGroup);
        if(patients.isEmpty()){
            throw new ResourceNotFoundException("No patients found with blood group: "+bloodGroup);
        }
        return patients.stream().map(patientMapper::toResponse).toList();
    }



}
