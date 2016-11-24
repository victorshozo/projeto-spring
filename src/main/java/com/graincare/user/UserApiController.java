package com.graincare.user;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.graincare.mail.Email;
import com.graincare.mail.EmailSender;

@RestController
public class UserApiController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private EmailSender emailSender;

	@RequestMapping(value = "/user/password/reset", method = RequestMethod.POST)
	public void sendPassword(@RequestBody EmailPasswordDTO dto) {
		Optional<User> user = userRepository.findByEmail(dto.getEmail());
		if (user.isPresent()) {
			String password = user.get().getDecryptedpassword();

			Map<String, Object> payload = new HashMap<>();
			payload.put("password", password);
			Email email = new Email(dto.getEmail(), "Lembrete de senha GrainCare", payload, "reset_password");
			emailSender.send(email);
		} else {
			//throw new UsernameNotFoundException(msg)
		}
	}
}
