package com.example.session.service;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.session.dto.LoginApiData;
import com.example.session.dto.SignupApiData;
import com.example.session.entity.User;
import com.example.session.repository.UserRepository;

@Service
public class AppService {

	private final UserRepository userRepository;
	
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	
	public AppService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public void handleCreateUserAccount(SignupApiData signupApiData) throws Exception {
		
		Optional<User> dbData = userRepository.findByEmail(signupApiData.getEmail());
		
		if(dbData.isEmpty()) {
			User userObj = new User();
			
			userObj.setName(signupApiData.getName());
			userObj.setEmail(signupApiData.getEmail());
			userObj.setMobile(signupApiData.getMobile());
			userObj.setPassword(passwordEncoder.encode(signupApiData.getPassword()));
			
			userRepository.save(userObj);
		} else {	
			throw new Exception("User already exists please login!");
		}
	}
	
	public User handleUserLogin(LoginApiData loginApiData) throws Exception {
		
		Optional<User> dbData = userRepository.findByEmail(loginApiData.getEmail());
		
		if(!dbData.isEmpty()) {
			User userData = dbData.get();
			
			if(passwordEncoder.matches(loginApiData.getPassword(), userData.getPassword())) {
//				System.out.println("password matches");
				return userData;
			}
			throw new Exception("Password doesnot matches, Please try again!!");
		}
		throw new Exception("Email does not exists please try again");
	}

	public User handleUserProfile(Integer userId) {
		
		Optional<User> dbData = userRepository.findById(userId);
		if(!dbData.isEmpty()) {
			User userData = dbData.get();
			
			return userData;
		}
		throw new RuntimeException("user not found");
	}
}
