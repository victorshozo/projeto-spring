package com.graincare.mail;

import java.util.Map;

public class Email {

	private String to;
	private String subject;
	private String templateName;
	private Map<String, Object> payload;

	public Email(String to, String subject, Map<String, Object> payload, String templateName) {
		this.to = to;
		this.subject = subject;
		this.payload = payload;
		this.templateName = templateName;
	}

	public String getTo() {
		return to;
	}

	public String getSubject() {
		return subject;
	}

	public String getTemplateName() {
		return templateName;
	}

	public Map<String, Object> getPayload() {
		return payload;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public void setPayload(Map<String, Object> payload) {
		this.payload = payload;
	}

}
