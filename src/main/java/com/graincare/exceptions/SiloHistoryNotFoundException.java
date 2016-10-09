package com.graincare.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Silo History não encontrado!")
public class SiloHistoryNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

}
