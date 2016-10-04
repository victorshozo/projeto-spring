package com.graincare.silos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = SiloHistory.class, idClass = Long.class)
public interface SiloHistoryRepository {

	List<SiloHistory> findAll();

	List<SiloHistory> findAllByOpenTrue();

	Optional<SiloHistory> findById(Long siloHistoryId);

}
