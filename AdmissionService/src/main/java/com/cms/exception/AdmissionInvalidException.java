package com.cms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class AdmissionInvalidException extends Exception {
	
public AdmissionInvalidException(String message) {
	super(message);
}

public AdmissionInvalidException() {
	
}

}
