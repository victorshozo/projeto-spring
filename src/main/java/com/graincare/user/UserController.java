package com.graincare.user;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;

import com.graincare.exceptions.PasswordsDontMatchException;
import com.graincare.exceptions.UserNotfoundException;
import com.graincare.farm.Farm;

@Controller

public class UserController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserToUserListDTOConverter converter;
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(path = "/cadastro/usuario", method = GET)
	public String userPage(Model model) {
		model.addAttribute(new UserDTO());
		return "cadastro/user";
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(path = "/users", method = POST)
	public String newUser(@ModelAttribute UserDTO dto) {
		List<Farm> farms = new ArrayList<>();
		User user = new User(dto.getEmail(), dto.getPassword());

		dto.getFarmNames().forEach(name -> {
			farms.add(new Farm(name, user));
		});
		user.setFarms(farms);
		userRepository.save(user);

		return "cadastro/user";
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(path = "/usuarios", method = GET)
	public ModelAndView getAllusers() {
		ModelAndView modelAndView = new ModelAndView("users");

		List<UserListDTO> users = new ArrayList<>();
		userRepository.findAllByRole("ROLE_USER").forEach(user -> {
			UserListDTO dto = converter.convert(user);
			users.add(dto);
		});
		modelAndView.addObject("users", users);

		return modelAndView;
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(path = "/usuario/{userId}", method = GET)
	public ModelAndView getUserPageFor(@PathVariable Long userId) {
		ModelAndView modelAndView = new ModelAndView("edit/user-edit");

		Optional<User> user = userRepository.findById(userId);
		if (user.isPresent()) {
			modelAndView.addObject("user", user.get());
		}
		return modelAndView;
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(path = "/users/{userId}", method = POST)
	public ModelAndView updateUser(@ModelAttribute UserDTO dto, @PathVariable Long userId) {
		ModelAndView modelAndView = new ModelAndView("edit/user-edit");

		Optional<User> optionalUser = userRepository.findById(userId);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			user.setEmail(dto.getEmail());
			if (dto.getPassword() != null && !"".equals(dto.getPassword().trim())) {
				user.setPassword(dto.getPassword());
			}
			userRepository.save(user);
			modelAndView.addObject("user", user);
		}

		return modelAndView;
	}
	
	@RequestMapping(path = "/trocar-senha", method = GET)
	public ModelAndView getPasswordChange(@RequestParam String hash) {
		ModelAndView modelAndView = new ModelAndView("change_password");
		Long userId = Long.valueOf(new String(Base64.decodeBase64(hash), UTF_8));
		Optional<User> user = userRepository.findById(userId);
		if(user.isPresent()) {
			modelAndView.addObject("userId", userId);
			modelAndView.addObject("userEmail", user.get().getEmail());
		}
		return modelAndView;
	}
	
	@RequestMapping(path = "/user/{userId}/password", method = POST)
	public ModelAndView updatePassword(@ModelAttribute PasswordChangeDTO dto, @PathVariable Long userId) {
		if(!StringUtils.equals(dto.getPassword(), dto.getCheckPassword())) {
			throw new PasswordsDontMatchException();
		}
		User user = userRepository.findById(userId).orElseThrow(UserNotfoundException::new);
		
		user.setPassword(dto.getPassword());
		userRepository.save(user);
		
		return new ModelAndView("change_password");
	}
}
