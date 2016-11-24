package com.graincare.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Esse sensor não pode ser excluido pois esta em uso")
public class SensorInUseException extends RuntimeException {
	private static final long serialVersionUID = 1L;
}
