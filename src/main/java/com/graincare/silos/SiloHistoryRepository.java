package com.graincare.silos;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

@RepositoryDefinition(domainClass = SiloHistory.class, idClass = Long.class)
public interface SiloHistoryRepository {

	List<SiloHistory> findBySiloFarmUserId(Long userId);
	
	List<SiloHistory> findByOpenFalseAndSiloFarmUserId(Long userId);

	Optional<SiloHistory> findBySiloIdAndOpenFalseAndSiloFarmUserId(Long siloId, Long userId);

	SiloHistory save(SiloHistory siloHistory);
	
	@Query(value = "select s.id as siloId,  "
			+ " date(s.closed_at) closedAt,  " 
			+ " date(s.opened_at) openAt, " 
			+ " s.grao grain, "
			+ " sum(b.temperature) / sum(1) as averageTemperature, " 
			+ " sum(b.humidity) / sum(1) as averageHumidity, "
			+ " (((select max(sh.distance) from sensor_history sh  "
			+ " 	where sh.silo_history_id = s.id and sh.humidity is null and sh.temperature is null "
			+ "		group by sh.silo_history_id) * 100) / (select sl.size from silo sl where sl.id = s.id)) capacity "
			+ " from silo_history s " 
			+ " inner join sensor_history b on b.silo_history_id = s.id "
			+ " and b.humidity is not null " 
			+ " and b.temperature is not null " 
			+ "where s.open = true "
			+ " and date(s.closed_at) between date(:starDate) and date(:endDate) " 
			+ " and s.silo_id = :siloId "
			+ " group by s.id ", nativeQuery = true)
	List<Object[]> generateReportFor(@Param("siloId") Long siloId,  @Param("starDate") Date startDate,
			@Param("endDate") Date endDate);
	
	@Query(value = "select sum(b.humidity) / sum(1) averagehumidity, "
			+ " sum(b.temperature) / sum(1) averageTemperature "
			+ " from sensor_history b "
			+ " inner join silo_history s on s.id = b.silo_history_id "
			+ " where date(s.closed_at) between date(:starDate) and date(:endDate) "
			+ " and b.distance is null "
			+ "	and s.silo_id = :siloId "
			+ " group by date(b.updated_at);", nativeQuery = true)
	List<Object[]> generateGraphicFor(@Param("siloId") Long siloId, @Param("starDate") Date startDate, 
			@Param("endDate") Date endDate);

	List<SiloHistory> findBySiloId(Long siloId);

	List<SiloHistory> findByOpenFalseAndSiloFarmId(Long farmId);
}
