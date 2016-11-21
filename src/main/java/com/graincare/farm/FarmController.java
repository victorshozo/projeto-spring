package com.graincare.farm;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.graincare.user.User;
import com.graincare.user.UserRepository;

@Controller
public class FarmController {

	@Autowired
	private FarmRepository farmRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private FarmToFarmDTOConverter converter;

	@RequestMapping(path = "/cadastro/fazenda/user/{userId}", method = GET)
	public ModelAndView getNewFarmPageFor(@PathVariable Long userId) {
		ModelAndView modelAndView = new ModelAndView("cadastro/farm-user");
		Optional<User> user = userRepository.findById(userId);
		if (user.isPresent()) {
			modelAndView.addObject("userId", user.get().getId());
			modelAndView.addObject("userEmail", user.get().getEmail());
		}

		return modelAndView;
	}

	@RequestMapping(path = "/cadastro/fazenda/user/{userId}", method = POST)
	public ModelAndView saveFarmFor(@PathVariable Long userId, @ModelAttribute FarmDTO dto){
		ModelAndView modelAndView = new ModelAndView("farms");
		Optional<User> user = userRepository.findById(userId);
		if(user.isPresent()) {
			Farm farm = new Farm(dto.getName(), user.get());
			farmRepository.save(farm);
			
			modelAndView.addObject("userId", user.get().getId());
			modelAndView.addObject("userEmail", user.get().getEmail());
		}
		return modelAndView;
	}

	@RequestMapping(path = "/usuario/{userId}/fazendas", method = GET)
	public ModelAndView getFarmsOfUser(@PathVariable Long userId) {
		ModelAndView modelAndView = new ModelAndView("farms");
		Optional<User> user = userRepository.findById(userId);
		if (user.isPresent()) {
			modelAndView.addObject("userEmail", user.get().getEmail());
			modelAndView.addObject("userId", user.get().getId());

			List<FarmDTO> farms = new ArrayList<>();
			user.get().getFarms().forEach(farm -> {
				FarmDTO dto = converter.convert(farm);
				farms.add(dto);
			});

			modelAndView.addObject("farms", farms);
		}
		return modelAndView;
	}

	@RequestMapping(path = "/fazenda/{farmId}", method = GET)
	public ModelAndView getUserPageFor(@PathVariable Long farmId) {
		ModelAndView modelAndView = new ModelAndView("edit/farm-edit");

		Optional<Farm> farm = farmRepository.findById(farmId);
		if (farm.isPresent()) {
			modelAndView.addObject("farm", farm.get());
		}
		return modelAndView;
	}

	@RequestMapping(path = "/farms/{farmId}", method = POST)
	public ModelAndView update(@ModelAttribute FarmDTO dto, @PathVariable Long farmId) {
		ModelAndView modelAndView = new ModelAndView("edit/farm-edit");
		Optional<Farm> optionalFarm = farmRepository.findById(farmId);
		if (optionalFarm.isPresent()) {
			Farm farm = optionalFarm.get();
			farm.setName(dto.getName());
			farmRepository.save(farm);

			modelAndView.addObject("farm", farm);
		}

		return modelAndView;
	}
}
