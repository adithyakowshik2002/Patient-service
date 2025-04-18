package com.improveid.HMS.Patient_Service.Entity;

import com.improveid.HMS.Patient_Service.Enums.BloodGroup;
import com.improveid.HMS.Patient_Service.Enums.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

@Entity
@Table(name="PATIENTS")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder//this will give the builder pattern implementation of the class
public class PatientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Patient_ID")
    private Long id;


    //private String Doctorname;
    @Column(name = "first_name")
    @NotBlank(message = "First name is required")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender",nullable = false)
    private Gender gender;

    @DateTimeFormat(pattern = "YYYY-MM-DD")
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;


    @Column(name = "age")
    private Integer age;

    @Pattern(regexp = "\\d{12}",message = "Aadhar must be digits")
    @Column(unique = true)
    private String aadhar;

    @Email(regexp = ".*@gmail\\.com$",message = "Email must end with @gmail.com")
    @Column(unique = true,nullable = false)
    private String email;

    @Pattern(regexp = "\\d{10}",message = "Phone number must be 10 digits")
    @Column(name = "phone_number",unique = true)
    private String phoneNumber;

    @Pattern(regexp = "^[a-zA-Z0-9,.-]*$",message = "Address contains invalid characters")
    @Column(name = "address")
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "blood_group")
    private BloodGroup bloodGroup;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "patient",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Appointment> appointments;

    @PrePersist
    @PreUpdate
    public void AgeCalulate(){
        if(dateOfBirth !=null) {
            this.age = Period.between(dateOfBirth, LocalDate.now()).getYears();
        }
        else {
           this.age=0;
        }
    }

}
