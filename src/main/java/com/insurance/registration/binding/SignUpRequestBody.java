package com.insurance.registration.binding;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Data
public class SignUpRequestBody {


    private String fullName;
    private String emailId;
    private Long mobileNumber;
    private LocalDate dateOfBirth;
    private Long ssn;
    private String gender;

}
