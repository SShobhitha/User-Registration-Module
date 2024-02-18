package com.insurance.registration.controller;

import com.insurance.registration.binding.*;
import com.insurance.registration.service.UserRegistrationImplService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserRegistartionController {

    private static final Logger logger= LoggerFactory.getLogger(UserRegistartionController.class);

    @Autowired
    private UserRegistrationImplService userRegistrationImplService;


    @PostMapping("/signup")
    public ResponseEntity<SignUpResponse> signUp(@RequestBody SignUpRequestBody signUpRequestBody){
        logger.info("**Inside <sign up api> UserRegistartionController SignUp(signUprequestBody");
        SignUpResponse signUpResponse=userRegistrationImplService.signUp(signUpRequestBody);
        if(signUpResponse.getSuccessMsg()!=null)
            return new ResponseEntity<>(signUpResponse, HttpStatus.CREATED);
        else if(signUpResponse.getErrorMsg().equals("The account already exist with an email id")){
            return new ResponseEntity<>(signUpResponse,HttpStatus.BAD_REQUEST);
        }
        else
            return new ResponseEntity<>(signUpResponse,HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequestBody loginRequestBody){
        logger.info("**Inside <sign up api> UserRegistartionController login(loginRequestBody");
        LoginResponse response= userRegistrationImplService.login(loginRequestBody);
        if(response.getResult().contains("Login Successful") ){
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else if(response.getResult().contains("Please change the password from default one")){
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
        else{
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/forgotpassword/{email}")
    public String forgotPazword(@PathVariable String email){
        logger.info("**Inside <sign up api> UserRegistartionController forgotPazzword(email)**");
        return userRegistrationImplService.forgetPazzword(email);

    }

    @PostMapping("/update")
    public ResponseEntity<LoginResponse> updatePazzword(@RequestBody UpdatePazzword updatePazzword){
        logger.info("**Inside <sign up api> UserRegistartionController updatePazzword(updatePazzword)**");
        LoginResponse response=userRegistrationImplService.updatePazzword(updatePazzword);
        if(response.getResult().contains("The password has been changed successful")){
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        else if(response.getResult().contains("The password has been alread changed")){
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
        else{
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

