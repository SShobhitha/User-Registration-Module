package com.insurance.registration.binding;

import lombok.Data;

@Data
public class UpdatePazzword {

    private String newpazzword;
    private String confirmPazzword;
    private String emailId;
    private Long userId;
}
