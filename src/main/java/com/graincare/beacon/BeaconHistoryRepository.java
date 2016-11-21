package com.graincare.beacon;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

@RepositoryDefinition(domainClass = BeaconHistory.class, idClass = Long.class)
public interface BeaconHistoryRepository {
	
	BeaconHistory save(BeaconHistory beaconHistory);
	
	List<BeaconHistory> findByBeaconFarmUserId(Long userId);

	List<BeaconHistory> findByBeaconId(Long beaconId);
	
	Optional<BeaconHistory> findTopByBeaconFarmUserIdAndBeaconIdOrderByUpdatedAtDesc(Long userId, Long beaconId);
	
	@Query(value = "select date(b.`updated_at`) as date, " 
					+ " sum(1) as quantity,"
					+ " sum(b.`temperature`) / sum(1) as averageTemperature,"
					+ " sum(b.`humidity`) / sum(1) as averageHumidity"
					+ " from `beacon_history` b "
					+ " where b.`silo_history_id` = :siloHistoryId "
					+ " and b.`humidity` is not null "
					+ " and b.`temperature` is not null "
					+ " group by date(b.`updated_at`)", nativeQuery = true)
	List<Object[]> getListOfAverageTemperatureAndHumidityFor(@Param("siloHistoryId") Long siloHistoryId);

	List<BeaconHistory> findByBeaconIn(List<Beacon> beacons);
	
	@Query(value = "delete from beacon_history b where b.beacon_id in(:beaconIds)", nativeQuery = true)
	void delete(@Param("beaconIds") List<Long> beaconIds);
}
