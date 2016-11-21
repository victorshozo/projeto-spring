package com.graincare.user;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.graincare.farm.Farm;

@Controller
@Secured("ROLE_ADMIN")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserToUserListDTOConverter converter;

	@RequestMapping(path = "/cadastro/usuario", method = GET)
	public String userPage(Model model) {
		model.addAttribute(new UserDTO());
		return "cadastro/user";
	}

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

	@RequestMapping(path = "/usuario/{userId}", method = GET)
	public ModelAndView getUserPageFor(@PathVariable Long userId) {
		ModelAndView modelAndView = new ModelAndView("edit/user-edit");

		Optional<User> user = userRepository.findById(userId);
		if (user.isPresent()) {
			modelAndView.addObject("user", user.get());
		}
		return modelAndView;
	}

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
	
	@RequestMapping(path = "/users/{userId}/delete", method = POST)
	public ModelAndView updateUser(@PathVariable Long userId) {
		ModelAndView modelAndView = new ModelAndView("users");
		Optional<User> user = userRepository.findById(userId);
		if(user.isPresent()){
			// TODO - delete user
		}

		return modelAndView;
	}
}
