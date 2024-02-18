package com.insurance.registration.repository;

import com.insurance.registration.entity.UserDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetailsEntity,Long> {


    public Optional<UserDetailsEntity> findByUserIdAndEmailId(Long userId,String emailId);
    public Optional<UserDetailsEntity> findByEmailId(String emailId);


}
