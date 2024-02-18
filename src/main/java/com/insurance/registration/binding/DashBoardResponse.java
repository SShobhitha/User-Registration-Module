package com.insurance.registration.binding;

import lombok.Data;

@Data
public class DashBoardResponse {
    private Long plansNumber;
    private Long citizenApprovedcount;
    private Long citizenDeniedCount;
    private Long benefitsCount;
}
