package com.graincare.exceptions;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = NOT_FOUND, reason = "Nenhum registro encontrado com o id informado")
public class SensorHistoryNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
}
