package com.graincare.farm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.graincare.user.LoggedUser;

@RestController
public class FarmApiController {
	
	@Autowired
	private FarmRepository farmRepository;
	@Autowired
	private LoggedUser loggedUser;
	
	@RequestMapping(path = "/user/farms", produces = "application/json", method = RequestMethod.GET)
	public List<Farm> getFarmsOf() {
		List<Farm> farms = farmRepository.findByUserId(loggedUser.get().getId());
		return farms;
	}

}
