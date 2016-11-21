package com.graincare.farm;

import java.util.Optional;

import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Farm.class, idClass = Long.class)
public interface FarmRepository {
	
	Optional<Farm> findById(Long id);

	void save(Farm farm);

	void delete(Farm farm);
}
