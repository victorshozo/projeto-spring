package com.graincare.sensor;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

@RepositoryDefinition(domainClass = Sensor.class, idClass = Long.class)
public interface SensorRepository {

	List<Sensor> findByFarmUserId(Long userId);
	
	List<Sensor> findByFarmId(Long farmId);
	
	List<Sensor> findByFarmUserIdAndIdIn(Long userId, List<Long> ids);
	
	void save(Sensor sensor);
	
	@Query(value = "update sensor set deleted = 1 where id = :sensorId", nativeQuery = true)
	@Modifying
	void delete(@Param("sensorId") Long sensorId);
}
