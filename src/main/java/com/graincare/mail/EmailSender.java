package com.graincare.mail;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailSender {
	private static final Logger LOGGER = LoggerFactory.getLogger(EmailSender.class);
	private static final String UTF_8_CHARSET = "UTF-8";
	
	@Autowired
	private TemplateProcessor templateProcessor;
	
	@Value("${email.debug}")
	private boolean emailDebug;
	@Value("${email.hostname}")
	private String hostName;
	@Value("${email.port}")
	private String port;
	@Value("${email.name}")
	private String name;
	@Value("${email.username}")
	private String userName;
	@Value("${email.password}")
	private String password;
	
	public HtmlEmail createHtmlEmail(){
		HtmlEmail htmlEmail = new HtmlEmail();
		htmlEmail.setDebug(emailDebug);
		htmlEmail.setHostName(hostName);
		htmlEmail.setSSLOnConnect(true);
		htmlEmail.setSslSmtpPort(port);
		htmlEmail.setAuthenticator(new DefaultAuthenticator(userName, password));
		htmlEmail.setCharset(UTF_8_CHARSET);
		try {
			htmlEmail.setFrom(userName, name);
		} catch (EmailException e) {
			LOGGER.warn("Unable to create HtmlEmail due invalid email on property email.username");
		}
		
		return htmlEmail;
	}
	
	public void send(Email email) {		
		String html = templateProcessor.writeHtml(email);
		
		HtmlEmail htmlEmail = createHtmlEmail();
		htmlEmail.setSubject(email.getSubject());
		try {
			htmlEmail.setHtmlMsg(html);
			htmlEmail.addTo(email.getTo());
			htmlEmail.send();
			LOGGER.info("Sent email to {}", email.getTo());
		} catch (EmailException e) {
			LOGGER.error("Unable to send email to {}", email.getTo());
		}
	}
}
