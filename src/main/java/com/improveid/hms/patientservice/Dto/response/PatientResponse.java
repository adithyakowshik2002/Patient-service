package com.improveid.hms.patientservice.Dto.response;

import com.improveid.hms.patientservice.Entity.Appointment;
import com.improveid.hms.patientservice.Enums.BloodGroup;
import com.improveid.hms.patientservice.Enums.Gender;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private Gender gender;
    private LocalDate dateOfBirth;
    private Integer age;
    private String aadhar;
    private String email;
    private String phoneNumber;
    private String address;
    private BloodGroup bloodGroup;
    private List<Appointment> appointments;
}
