package com.graincare.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "N�o existe gr�o desse tipo no repositorio")
public class GrainNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
}
