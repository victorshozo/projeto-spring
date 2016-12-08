package com.graincare.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Informe um nome para a fazenda")
public class InvalidFarmNameException extends RuntimeException {
	private static final long serialVersionUID = 1L;
}
