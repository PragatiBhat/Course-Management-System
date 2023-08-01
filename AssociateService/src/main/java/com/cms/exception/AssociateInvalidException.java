package com.cms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class AssociateInvalidException extends Exception {

	
	public AssociateInvalidException(String message)
	{
		super(message);
	}
}
