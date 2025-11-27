package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entities.users;
import com.example.demo.services.UserService;

@Controller
public class UserController {
	
	@Autowired
	UserService userServ;
	
	
	@GetMapping("/")
	public String showSignUpPage() {
		return "index";
	}
	
	@PostMapping("/register")
	public String registerUser(@ModelAttribute users user) {
		
		
		userServ.saveuser(user);
		
		return "login";
	}

	@PostMapping("/login")
	public String loginuser(@RequestParam String emailId, @RequestParam String password) {
		boolean isValid = userServ.loginAndGenerateOTP(emailId, password);
		
		if(isValid) {
			return "otp";
			
		} else {
			return "loginfail";
		}
		
	}
	@GetMapping("/login")
	String showLoginPage() {
		return "login";
	}
	
	@PostMapping("/verifyOTP")
	public String verifyOtp(@RequestParam String otp) {
		boolean isCorrect = userServ.verifyOtp(otp);
		if(isCorrect) {
			return "home";
		} else {
			return "loginfail";
		}
	}
}
