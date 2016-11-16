package com.graincare.mail;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

@Component
class PayloadToContextConverter {
	
	Context convert(Map<String, Object> payload) {
		Context context = new Context();
		payload.entrySet().forEach(keyValue -> {
			context.setVariable(keyValue.getKey().toString(), keyValue.getValue());
		});
		return context;
	}

}