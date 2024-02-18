package com.insurance.registration.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Table(name="CITIZEN_DETAILS")
@Data
public class UserDetailsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @Column(name="FULL_NAME")
    private String fullName;

    @Column(name="EMAIL_ID")
    private String emailId;

    @Column(name="PHONE_NUMBER")
    private Long phoneNumber;

    @Column(name="GENDER",length = 1)
    private String gender;

    @Column(name="DATE_OF_BIRTH",length = 10)
    private LocalDate dateOfBirth;

    @Column(name="SSN_ID")
    private Long ssnId;

    @Column(name="PASSWORD")
    private String pazzword;

    @Column(name="USER_TYPE")
    private String userType;

    @Column(name="LOGIN_COUNT")
    private Integer LoginCount;

    @Column(name="CREATED_DATE",updatable = false)
    @CreationTimestamp
    private LocalDate createdDate;

    @Column(name="CREATED_BY")
    private String createdBy;

    @Column(name="UPDATED_DATE")
    private LocalDate updateDate;

    @Column(name="UPDATED_BY")
    private String updatedBy;



}
