package com.graincare.silos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Silo.class, idClass = Long.class)
public interface SiloRepository {

	List<Silo> findByFarmUserId(Long userId);

	Optional<Silo> findByIdAndFarmUserId(Long id, Long userId);
}
