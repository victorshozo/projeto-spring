package com.graincare.user;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

import com.graincare.mail.Email;
import com.graincare.mail.EmailSender;

@RestController
public class UserApiController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private EmailSender emailSender;
	@Value("${server.url}")
	private String serverUrl;

	@RequestMapping(value = "/user/password/reset", method = RequestMethod.POST)
	public void sendPassword(@RequestBody EmailPasswordDTO dto) {
		Optional<User> user = userRepository.findByEmail(dto.getEmail());
		if (user.isPresent()) {
			String userHash = user.get().getHashedUserId();
			String url = StringUtils.concat(serverUrl, "trocar-senha?hash=", userHash);
			
			Map<String, Object> payload = new HashMap<>();
			payload.put("url", url);
			Email email = new Email(dto.getEmail(), "Recuperação de senha GrainCare", payload, "reset_password");
			emailSender.send(email);
		}
	}
}
