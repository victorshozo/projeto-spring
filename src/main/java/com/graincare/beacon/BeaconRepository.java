package com.graincare.beacon;

import java.util.List;

import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Beacon.class, idClass = Long.class)
public interface BeaconRepository {

	List<Beacon> findByFarmUserId(Long userId);
	
	List<Beacon> findByFarmId(Long farmId);
	
	List<Beacon> findByFarmUserIdAndIdIn(Long userId, List<Long> ids);
	
	void save(Beacon beacon);

	void delete(Beacon beacon);
	
}
