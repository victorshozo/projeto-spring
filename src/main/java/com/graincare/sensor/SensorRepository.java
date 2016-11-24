package com.graincare.sensor;

import java.util.List;

import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Sensor.class, idClass = Long.class)
public interface SensorRepository {

	List<Sensor> findByFarmUserId(Long userId);
	
	List<Sensor> findByFarmId(Long farmId);
	
	List<Sensor> findByFarmUserIdAndIdIn(Long userId, List<Long> ids);
	
	void save(Sensor sensor);

	void delete(Sensor sensor);
	
}
