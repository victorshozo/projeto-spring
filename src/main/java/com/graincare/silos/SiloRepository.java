package com.graincare.silos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

@RepositoryDefinition(domainClass = Silo.class, idClass = Long.class)
public interface SiloRepository {

	List<Silo> findByFarmUserId(Long userId);
	
	List<Silo> findByFarmId(Long farmId);

	Optional<Silo> findByIdAndFarmUserId(Long id, Long userId);

	void save(Silo silo);

	Optional<Silo> findById(Long siloId);

	@Modifying
	@Query(value = "update silo set deleted = 1 where id = :siloId", nativeQuery = true)
	void delete(@Param("siloId") Long siloId);
}
