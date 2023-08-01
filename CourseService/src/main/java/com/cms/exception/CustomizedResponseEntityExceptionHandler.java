package com.cms.exception;



import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {


	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception except, WebRequest request) {

		ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDate.now(), "Internal Server Error",
				request.getSessionId(),HttpStatus.INTERNAL_SERVER_ERROR.toString());
		log.error(except.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(CourseInvalidException.class)
	public final ResponseEntity<ExceptionResponse> handleNotFoundException(CourseInvalidException except, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDate.now(), except.getMessage(),
				request.getSessionId(), HttpStatus.NOT_FOUND.toString());
		log.error(except.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
	}	
	
		



}


