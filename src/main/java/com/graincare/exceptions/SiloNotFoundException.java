package com.graincare.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Silo não encontrado!")
public class SiloNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

}
