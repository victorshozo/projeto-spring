package com.graincare.silos;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.graincare.farm.Farm;
import com.graincare.farm.FarmRepository;
import com.graincare.user.LoggedUser;
import com.graincare.user.User;
import com.graincare.user.UserRepository;

@Controller
@Secured("ROLE_ADMIN")
public class SiloController {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private FarmRepository farmRepository;
	@Autowired
	private SiloRepository siloRepository;
	@Autowired
	private LoggedUser loggedUser;
	
	@RequestMapping(path = "/cadastro/silo", method = GET)
	public ModelAndView getSiloPage() {
		ModelAndView modelAndView = new ModelAndView("cadastro/silo");
		List<User> users = userRepository.findAllByRole("ROLE_USER");
		
		modelAndView.addObject("users", users);
		return modelAndView;
	}
	
	@RequestMapping(path = "/cadastro/silo/user/{userId}", method = GET)
	public ModelAndView getSiloPageForUser(@PathVariable Long userId) {
		ModelAndView modelAndView = new ModelAndView("cadastro/silo-user");
	
		System.out.println(loggedUser.get().getAuthorities());
		
		Optional<User> user = userRepository.findById(userId);
		if(user.isPresent()) {
			List<Farm> farms = user.get().getFarms();
			modelAndView.addObject("farms", farms);
			modelAndView.addObject("userEmail", user.get().getEmail());
		}
		
		return modelAndView;
	}
	
	@RequestMapping(path = "/silos", method = POST)
	public String newSilo(@ModelAttribute SiloDTO dto) {
		Optional<Farm> farm = farmRepository.findById(dto.getFarmId());
		if(farm.isPresent()) {
			Silo silo = new Silo(farm.get(), dto.getRegion(), dto.getSize(), dto.getCapacity());
			siloRepository.save(silo);
		}
		return "cadastro/silo-user";
	}
	
	@RequestMapping(path = "/fazenda/{farmId}/silos", method = GET)
	public ModelAndView getFarmsOfUser(@PathVariable Long farmId) {
		ModelAndView modelAndView = new ModelAndView("silos");
		
		Optional<Farm> farm = farmRepository.findById(farmId);
		if (farm.isPresent()) {
			modelAndView.addObject("farmName", farm.get().getName());
			List<Silo> silos = siloRepository.findByFarmId(farm.get().getId());
		
			modelAndView.addObject("silos", silos);
		}
		return modelAndView;
	}
	
	@RequestMapping(path = "/silo/{siloId}", method = GET)
	public ModelAndView getUserPageFor(@PathVariable Long siloId) {
		ModelAndView modelAndView = new ModelAndView("edit/silo-edit");

		Optional<Silo> silo = siloRepository.findById(siloId);
		if (silo.isPresent()) {
			modelAndView.addObject("silo", silo.get());
		}
		return modelAndView;
	}
	
	@RequestMapping(path = "/silos/{siloId}", method = POST)
	public ModelAndView updateUser(@ModelAttribute SiloDTO dto, @PathVariable Long siloId) {
		ModelAndView modelAndView = new ModelAndView("edit/silo-edit");

		Optional<Silo> optionalSilo = siloRepository.findById(siloId);
		if (optionalSilo.isPresent()) {
			Silo silo = optionalSilo.get();
			silo.setRegion(dto.getRegion());
			silo.setSize(dto.getSize());
			silo.setCapacity(dto.getCapacity());
			siloRepository.save(silo);
			modelAndView.addObject("silo", silo);
		}

		return modelAndView;
	}
}
