package com.graincare.silos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = SiloHistory.class, idClass = Long.class)
public interface SiloHistoryRepository {

	List<SiloHistory> findBySiloFarmUserId(Long userId);
	
	List<SiloHistory> findByOpenFalseAndSiloFarmUserId(Long userId);

	Optional<SiloHistory> findBySiloIdAndOpenFalseAndSiloFarmUserId(Long siloId, Long userId);

	SiloHistory save(SiloHistory siloHistory);
}
