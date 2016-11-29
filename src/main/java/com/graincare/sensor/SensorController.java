
package com.graincare.sensor;

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
import com.graincare.user.User;
import com.graincare.user.UserRepository;

@Controller
@Secured("ROLE_ADMIN")
public class SensorController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private FarmRepository farmRepository;
	@Autowired
	private SensorRepository sensorRepository;

	@RequestMapping(path = "/cadastro/sensor", method = GET)
	public ModelAndView getSensorPage() {
		ModelAndView modelAndView = new ModelAndView("cadastro/sensor");
		List<User> users = userRepository.findAllByRole("ROLE_USER");

		modelAndView.addObject("users", users);
		return modelAndView;
	}

	@RequestMapping(path = "/cadastro/sensor/user/{userId}", method = GET)
	public ModelAndView getSensorPageFor(@PathVariable Long userId) {
		ModelAndView modelAndView = new ModelAndView("cadastro/sensor-user");

		Optional<User> user = userRepository.findById(userId);
		if (user.isPresent()) {
			List<Farm> farms = user.get().getFarms();
			modelAndView.addObject("farms", farms);
			modelAndView.addObject("userEmail", user.get().getEmail());
		}

		return modelAndView;
	}

	@RequestMapping(path = "/sensores", method = POST)
	public String newSensor(@ModelAttribute SensorDTO dto) {
		Optional<Farm> farm = farmRepository.findById(dto.getFarmId());
		if (farm.isPresent()) {
			for (int i = 0; i < dto.getQuantity(); i++) {
				sensorRepository.save(new Sensor(farm.get()));
			}
		}
		return "cadastro/sensor-user";
	}
	
	
	@RequestMapping(path = "/fazenda/{farmId}/sensores", method = GET)
	public ModelAndView getFarmsOfUser(@PathVariable Long farmId) {
		ModelAndView modelAndView = new ModelAndView("sensores");
		
		Optional<Farm> farm = farmRepository.findById(farmId);
		if (farm.isPresent()) {
			modelAndView.addObject("farmName", farm.get().getName());
			List<Sensor> sensores = sensorRepository.findByFarmId(farmId);
		
			modelAndView.addObject("sensores", sensores);
		}
		return modelAndView;
	}	
}
