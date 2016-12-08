package com.graincare.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "É preciso informar todos os campos")
public class InvalidDataException extends RuntimeException {
	private static final long serialVersionUID = 1L;
}
