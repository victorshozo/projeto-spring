package com.graincare.beacon;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "O mesmo beacon foi encontrado em mais de um silo ao mesmo tempo ou não está presente em nenhum silo")
public class InconsistentBeaconOnDatabaseException extends RuntimeException {
	private static final long serialVersionUID = 1L;
}
