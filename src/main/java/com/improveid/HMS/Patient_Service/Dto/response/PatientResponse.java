package com.improveid.HMS.Patient_Service.Dto.response;

import com.improveid.HMS.Patient_Service.Enums.BloodGroup;
import com.improveid.HMS.Patient_Service.Enums.Gender;
import lombok.*;

import java.time.LocalDate;
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
}
