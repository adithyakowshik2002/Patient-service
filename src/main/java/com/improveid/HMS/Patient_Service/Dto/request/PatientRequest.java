package com.improveid.HMS.Patient_Service.Dto.request;


import com.improveid.HMS.Patient_Service.Enums.BloodGroup;
import com.improveid.HMS.Patient_Service.Enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientRequest {

    @NotBlank(message = "First name is required")
    private String firstName;

    private String lastName;

    @NotNull(message = "Gender is required")
    private Gender gender;

    @NotNull(message = "Date of birth is required")
    private LocalDate dateOfBirth;

    @Pattern(regexp = "\\d{12}", message = "Aadhar must be digits")
    private String aadhar;

    @Email(regexp = ".*@gmail\\.com$", message = "Email must end with @gmail.com")
    @NotBlank(message = "Email is required")
    private String email;

    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    private String phoneNumber;

    @Pattern(regexp = "^[a-zA-Z0-9,.-]*$", message = "Address contains invalid characters")
    private String address;

    private BloodGroup bloodGroup;
}
