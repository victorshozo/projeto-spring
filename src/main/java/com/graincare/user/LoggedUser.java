package com.graincare.user;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class LoggedUser {

	public User get(){
		return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
}