package com.graincare.sensor;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

@RepositoryDefinition(domainClass = SensorHistory.class, idClass = Long.class)
public interface SensorHistoryRepository {
	
	SensorHistory save(SensorHistory sensorHistory);
	
	List<SensorHistory> findBySensorFarmUserId(Long userId);

	List<SensorHistory> findBySensorId(Long sensorId);
	
	Optional<SensorHistory> findTopBySensorFarmUserIdAndSensorIdOrderByUpdatedAtDesc(Long userId, Long sensorId);
	
	@Query(value = "select date(b.`updated_at`) as date, " 
					+ " sum(1) as quantity,"
					+ " sum(b.`temperature`) / sum(1) as averageTemperature,"
					+ " sum(b.`humidity`) / sum(1) as averageHumidity"
					+ " from `sensor_history` b "
					+ " where b.`silo_history_id` = :siloHistoryId "
					+ " and b.`humidity` is not null "
					+ " and b.`temperature` is not null "
					+ " group by date(b.`updated_at`)", nativeQuery = true)
	List<Object[]> getListOfAverageTemperatureAndHumidityFor(@Param("siloHistoryId") Long siloHistoryId);

	List<SensorHistory> findBySensorIn(List<Sensor> sensors);
	
	@Query(value = "delete from sensor_history b where b.sensor_id in(:sensorIds)", nativeQuery = true)
	void delete(@Param("sensorIds") List<Long> sensorIds);
}
