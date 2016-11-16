package com.graincare.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

@Configuration
public class TemplateEngineProducer {

	@Bean
	@Autowired
	public TemplateEngine createTemplateEngine(TemplateResolver templateResolver) {
		TemplateEngine templateEngine = new TemplateEngine();
		templateEngine.addTemplateResolver(templateResolver);

		return templateEngine;
	}

	@Bean(name = "templateResolver")
	public TemplateResolver emailTemplateResolver() {
		TemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		templateResolver.setPrefix("templates/");
		templateResolver.setTemplateMode("HTML5");
		templateResolver.setOrder(1);
		templateResolver.setCharacterEncoding("UTF-8");
		return templateResolver;
	}

}