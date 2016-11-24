package com.graincare.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Esse sensor não está presente em nenhum silo")
public class InconsistentSensorOnDatabaseException extends RuntimeException {
	private static final long serialVersionUID = 1L;
}
