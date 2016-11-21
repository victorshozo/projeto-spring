package com.graincare.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = User.class, idClass = Long.class)
public interface UserRepository {

	Optional<User> findByEmail(String email);
	
	Optional<User> findById(Long id);
	
	void save(User user);

	void delete(User user);

	List<User> findAllByRole(String role);
}
