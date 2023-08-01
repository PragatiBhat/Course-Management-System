package com.cms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CourseInvalidException  extends Exception{
	
	
	public CourseInvalidException(String message) {
		
		super(message);
		
	}

}
