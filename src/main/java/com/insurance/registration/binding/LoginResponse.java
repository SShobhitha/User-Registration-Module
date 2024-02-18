package com.insurance.registration.binding;

import lombok.Data;

@Data
public class LoginResponse {

    private Long userId;
    private String userType;
    private String fullName;
    private boolean isValid;
    private String result;
    private DashBoardResponse dashBoardResponse;

}
