package com.graincare.beacon;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Beacon não encontrado")
public class BeaconNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
}
