package com.insurance.registration.service;

import com.insurance.registration.binding.*;

public interface UserRegistrationService {

    public SignUpResponse signUp(SignUpRequestBody signUpRequestBody);

    public LoginResponse login(LoginRequestBody loginRequestBody);

    public String forgetPazzword(String email);

    public LoginResponse updatePazzword(UpdatePazzword updatePazzword);
}
