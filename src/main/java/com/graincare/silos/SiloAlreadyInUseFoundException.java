package com.graincare.silos;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Este silo está em uso no momento")
public class SiloAlreadyInUseFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
}
