package com.nabilanam.api.uselessapis.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ApiNotFoundException extends RuntimeException  {

	public ApiNotFoundException(String exception) {
		super(exception);
	}
}
