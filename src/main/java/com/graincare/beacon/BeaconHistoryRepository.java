package com.graincare.beacon;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

@RepositoryDefinition(domainClass = BeaconHistory.class, idClass = Long.class)
public interface BeaconHistoryRepository {

	List<BeaconHistory> findAll();

	List<BeaconHistory> findByBeaconId(Long beaconId);

	BeaconHistory save(BeaconHistory beaconHistory);

	@Query(value = "select date(b.`updated_at`) as date, " 
					+ " sum(1) as quantity,"
					+ " sum(b.`temperature`) / sum(1) as avarageTemperature,"
					+ " sum(b.`humidity`) / sum(1) as avarageHumidity"
					+ " from `beacon_history` b "
					+ " where b.`silo_history_id` = :siloHistoryId "
					+ " and b.`humidity` is not null "
					+ " and b.`temperature` is not null "
					+ " group by date(b.`updated_at`)", nativeQuery = true)
	List<Object[]> getListOfAvarageTemperatureAndHumidityFor(@Param("siloHistoryId") Long siloHistoryId);
	

}
