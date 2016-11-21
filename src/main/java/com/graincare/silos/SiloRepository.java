package com.graincare.silos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Silo.class, idClass = Long.class)
public interface SiloRepository {

	List<Silo> findByFarmUserId(Long userId);
	
	List<Silo> findByFarmId(Long farmId);

	Optional<Silo> findByIdAndFarmUserId(Long id, Long userId);

	void save(Silo silo);

	Optional<Silo> findById(Long siloId);

	void delete(Silo silo);
}
