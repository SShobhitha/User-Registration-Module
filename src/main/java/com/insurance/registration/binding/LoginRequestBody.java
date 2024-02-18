package com.insurance.registration.binding;


import lombok.Data;

@Data
public class LoginRequestBody {

    private String emailId;
    private String pazzword;
}
