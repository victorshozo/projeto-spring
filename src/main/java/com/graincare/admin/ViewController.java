package com.graincare.admin;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {
	
	@RequestMapping(path = "/home", method = GET)
	public String home(){
		return "home";
	}
	
	@RequestMapping(path = "/login", method = GET)
	public String login() {
		return "login";
	}
}
