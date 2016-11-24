package com.graincare.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "esse silo já está em uso")
public class SiloInUseException extends RuntimeException {
	private static final long serialVersionUID = 1L;
}
