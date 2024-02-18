package com.insurance.registration.service;

import com.insurance.registration.binding.*;
import com.insurance.registration.entity.UserDetailsEntity;
import com.insurance.registration.repository.UserDetailsRepository;
import com.insurance.registration.utils.EmailUtil;
import com.netflix.discovery.converters.Auto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Optional;
import java.util.Random;

@Service
public class UserRegistrationImplService implements UserRegistrationService{


    private static final Logger logger= LoggerFactory.getLogger(UserRegistrationImplService.class);


    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private EmailUtil emailUtil;

    @Override
    public SignUpResponse signUp(SignUpRequestBody signUpRequestBody) {
        boolean isSent = false;
        UserDetailsEntity userDetails = new UserDetailsEntity();
        SignUpResponse response = new SignUpResponse();
        Optional<UserDetailsEntity> userDetailsCheck = userDetailsRepository.findByEmailId(signUpRequestBody.getEmailId());
        logger.info("** Inside <sign up api> UserRegistartionController UserRegistrationImplService signUp(signUpRequestBody)**");
        if (userDetailsCheck.isPresent()) {
            response.setErrorMsg("The account already exist with an email id");
            return response;
        }
        BeanUtils.copyProperties(signUpRequestBody, userDetails);
        if (userDetails != null) {
            logger.info("** Inside If loop of <sign up api> UserRegistartionController UserRegistrationImplService signUp(signUpRequestBody)**");
            userDetails.setPazzword(generatePazzword(9));
            userDetails.setUserType("CITIZEN");
            userDetails.setLoginCount(0);
            userDetailsRepository.save(userDetails);
            String to = signUpRequestBody.getEmailId();
            String subject = "Password for your account";
            String fileName = "PASSWORD-FOR-NEW-ACCOUNT-EMAIL-BODY-TEMPLATE.txt";
            String body = readMailBodyContent(fileName, userDetails);
            isSent = emailUtil.sendEmail(to, subject, body);
            if (isSent) {
                response.setSuccessMsg("The account has been created successfully, Please login now");
            } else {
                response.setErrorMsg("Registration Falied");
            }
        }
        return response;

    }


    @Override
    public LoginResponse login(LoginRequestBody loginRequestBody) {
        LoginResponse loginResponse=new LoginResponse();
        Optional<UserDetailsEntity> userDetailsCheck=userDetailsRepository.findByEmailId(loginRequestBody.getEmailId());
        logger.info("** Inside <sign up api> UserRegistartionController UserRegistrationImplService login(loginRequestBody)**");
        if(userDetailsCheck.isPresent()){
            UserDetailsEntity userDetails=userDetailsRepository.findByEmailId(loginRequestBody.getEmailId()).get();
            if(userDetails.getPazzword().equals(loginRequestBody.getPazzword()) && userDetails.getLoginCount()>=1){
               loginResponse.setUserId(userDetails.getUserId());
               loginResponse.setUserType(userDetails.getUserType());
               loginResponse.setValid(true);
               DashBoardResponse dashBoardResponse=new DashBoardResponse();
               dashBoardResponse.setBenefitsCount(100L);
               dashBoardResponse.setCitizenApprovedcount(25890L);
               dashBoardResponse.setPlansNumber(345L);
               dashBoardResponse.setCitizenDeniedCount(90L);
               loginResponse.setDashBoardResponse(dashBoardResponse);
               loginResponse.setResult("Login Successful");
            }
            if (userDetails.getPazzword().equals(loginRequestBody.getPazzword()) && userDetails.getLoginCount()==0) {
                loginResponse.setResult("Please change the password from default one");
            }
        }
        else{
            loginResponse.setResult("The account doesn't exist");
        }

        return loginResponse;
    }

    @Override
    public LoginResponse updatePazzword(UpdatePazzword updatePazzword) {
        LoginResponse loginResponse=new LoginResponse();
        Optional<UserDetailsEntity> userDetailsCheck=userDetailsRepository.findByEmailId(updatePazzword.getEmailId());
        logger.info("** Inside <sign up api> UserRegistartionController UserRegistrationImplService login(loginRequestBody)**");
        if(userDetailsCheck.isPresent()){
            UserDetailsEntity userDetails= userDetailsRepository.findByEmailId(updatePazzword.getEmailId()).get();
            if(updatePazzword.getNewpazzword().equals(updatePazzword.getConfirmPazzword()) && userDetails.getLoginCount()==0){
                logger.info("** Inside If loop of <updatePazzword api> UserRegistartionController UserRegistrationImplService login(loginRequestBody)**");
                loginResponse.setUserId(userDetails.getUserId());
                loginResponse.setUserType(userDetails.getUserType());
                //loginResponse.setValid(true);
                DashBoardResponse dashBoardResponse=new DashBoardResponse();
                dashBoardResponse.setBenefitsCount(100L);
                dashBoardResponse.setCitizenApprovedcount(25890L);
                dashBoardResponse.setPlansNumber(345L);
                dashBoardResponse.setCitizenDeniedCount(90L);
                loginResponse.setDashBoardResponse(dashBoardResponse);
                loginResponse.setResult("The password has been changed successful");
                return loginResponse;
            }
            else{
                loginResponse.setResult("The password has been alread changed");
                return loginResponse;
            }

        }

        return loginResponse;

        }


    @Override
    public String forgetPazzword(String email) {
        Optional<UserDetailsEntity> userDetailsCheck=userDetailsRepository.findByEmailId(email);
        logger.info("** Inside <forgot api> UserRegistartionController in UserRegistrationImplService forgetPazzword(email)");
        if(userDetailsCheck.isPresent()){
            UserDetailsEntity userDetails=userDetailsRepository.findByEmailId(email).get();
            userDetails.setPazzword(generatePazzword(6));
            userDetails.setLoginCount(0);
            userDetailsRepository.save(userDetails);
            String to=email;
            String subject = "Password rest";
            String fileName = "PASSWORD-RESET-EMAIL-BODY-TEMPLATE.txt";
            String body = readMailBodyContent(fileName, userDetails);
            emailUtil.sendEmail(to,subject,body);
            return "Please check the email to reset a password";
        }
        return "The user doesn't have account, Please create an account with this email id";

    }

    private String generatePazzword(int length) {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = length;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;


    }

    private String readMailBodyContent(String filename, UserDetailsEntity entity) {
        String mailBody = "";
        try {
            StringBuilder sb = new StringBuilder();
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            mailBody = sb.toString();
            mailBody = mailBody.replace("{FULLNAME}", entity.getFullName());
            mailBody = mailBody.replace("{PWD}", entity.getPazzword());
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mailBody;
    }
}
