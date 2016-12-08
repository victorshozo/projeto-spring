package com.graincare.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Já existe um usuário com esse email")
public class UserAlreadyRegisteredException extends RuntimeException {
	private static final long serialVersionUID = 1L;
}
