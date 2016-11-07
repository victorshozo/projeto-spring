package com.graincare.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class TemplateProcessor {

	@Autowired
	private TemplateEngine templateEngine;
	@Autowired
	private PayloadToContextConverter converter;

	public String writeHtml(Email email) {
		Context context = new Context();
		context = converter.convert(email.getPayload());
		
		String templatePath = email.getTemplateName();
		
		return templateEngine.process(templatePath, context);
	}

}