package com.example.session.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.session.dto.LoginApiData;
import com.example.session.dto.SignupApiData;
import com.example.session.entity.User;
import com.example.session.service.AppService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/app")
public class AppController {
	
	private final AppService appService;
	
	public AppController(AppService appService) {
		this.appService = appService;
	}

	@PostMapping("/create-account")
	public ResponseEntity<?> createAccount(@RequestBody @Valid SignupApiData signupApiData) throws Exception{
		
		appService.handleCreateUserAccount(signupApiData);
		
		Map<String, String> responseMap = new HashMap<String, String>();
		responseMap.put("result", "success");
		responseMap.put("data", "User created");
		
		return ResponseEntity.ok().body(responseMap);
	}
	
	@PostMapping("/user-login")
	public ResponseEntity<?> userLogin(@RequestBody @Valid LoginApiData loginApiData,
			HttpServletRequest request) throws Exception{
		
		User userData = appService.handleUserLogin(loginApiData);
		
		HttpSession session = request.getSession(true);
		
		session.setAttribute("userId", userData.getId());
		session.setAttribute("userName", userData.getName());
		
		Map<String, String> responseMap = new HashMap<>();
		responseMap.put("result", "success");
		responseMap.put("data", "user login success");
		return ResponseEntity.ok().body(responseMap);
	}
	
	@GetMapping("/user-profile")
	public ResponseEntity<?> userProfile(HttpSession session){
		
		Integer userId = (Integer) session.getAttribute("userId");
		
		if(userId == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please login first");
		}
		
		User data = appService.handleUserProfile(userId);
		
		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("result", "success");
		responseMap.put("data", data);
		return ResponseEntity.ok().body(responseMap);
	}
	
	@PostMapping("/logout")
	public ResponseEntity<?> userLogout(HttpSession session){
		
		session.invalidate();
		
		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("result", "success");
		responseMap.put("data", "logout Success");
		return ResponseEntity.ok().body(responseMap);
	}
}