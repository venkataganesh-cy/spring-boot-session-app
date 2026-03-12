package com.example.session.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptions {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleApiInputValidationExceptions(MethodArgumentNotValidException ex){
		
		Map<String, String> errors = new HashMap<>();
		
		ex.getBindingResult().getFieldErrors().forEach(error -> {
			errors.put(error.getField(), error.getDefaultMessage());
		});
		
		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("result", "failed");
		responseMap.put("error", errors);
		
		return ResponseEntity.badRequest().body(responseMap);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleGeneralExceptions(Exception ex){
		
		Map<String, String> responseMap = new HashMap<>();
		
		responseMap.put("result", "failed");
		responseMap.put("message", ex.getMessage());
		
		return ResponseEntity.badRequest().body(responseMap);
	}
	
}
