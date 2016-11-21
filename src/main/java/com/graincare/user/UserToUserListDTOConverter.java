package com.graincare.user;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

@Service
public class UserToUserListDTOConverter implements Converter<User, UserListDTO> {

	@Override
	public UserListDTO convert(User user) {
		return new UserListDTO(user.getId(), user.getEmail(), user.getFarms().size());
	}

}
