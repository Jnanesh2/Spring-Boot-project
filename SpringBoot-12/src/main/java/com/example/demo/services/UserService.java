package com.example.demo.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.demo.entities.userotp;
import com.example.demo.entities.users;
import com.example.demo.repositories.UserOTPRepository;
import com.example.demo.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepo;
	@Autowired
	UserOTPRepository userOtpRepo;
	@Autowired
	JavaMailSender mailSender;
	public void saveuser(users user) {
		userRepo.save(user);
		
	}
	
	public boolean loginAndGenerateOTP(String emailId, String password) {
		users user = userRepo.findByEmailId(emailId);
		if(user == null) {
			return false;
		}
		if (!user.getPassword().equals(password)) {
			return false;
		}
		
		int otpNum = new Random().nextInt(100000, 1000000);
		String otp = String.valueOf(otpNum);
		
		userotp userOtp = new userotp();
		userOtp.setOtp(otp);
		userOtp.setUserId(user.getId());
		userOtpRepo.save(userOtp);
		
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(user.getEmailId());
		message.setSubject("Your OTP Code");
		message.setText("Your OTP code is:" + otp);
		mailSender.send(message);
		
		return true;
		
	}
	
	public boolean verifyOtp(String otp) {
		userotp userOtp = userOtpRepo.findByOtp(otp);
		if(otp.equals(userOtp.getOtp())) {
			return true;
		} else {
			return false;
		}
	}
}
