package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.userotp;

@Repository
public interface UserOTPRepository extends JpaRepository<userotp, Integer>{
	userotp findByOtp(String otp);

}
