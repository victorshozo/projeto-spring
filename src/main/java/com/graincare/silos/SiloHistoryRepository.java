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
			+ " (((select max(bh.distance) from beacon_history bh  "
			+ " 	where bh.silo_history_id = s.id and bh.humidity is null and bh.temperature is null "
			+ "		group by bh.silo_history_id) * 100) / (select sl.size from silo sl where sl.id = s.id)) capacity "
			+ " from silo_history s " 
			+ " inner join beacon_history b on b.silo_history_id = s.id "
			+ " and b.humidity is not null " 
			+ " and b.temperature is not null " + "where s.open = true "
			+ " and date(s.closed_at) between date(:starDate) and date(:endDate) " 
			+ " and s.silo_id = :siloId "
			+ " group by s.id ", nativeQuery = true)
	List<Object[]> generateReportFor(@Param("siloId") Long siloId,  @Param("starDate") Date startDate,
			@Param("endDate") Date endDate);
	
	@Query(value = "select sum(b.humidity) / sum(1) averagehumidity, "
			+ " sum(b.temperature) / sum(1) averageTemperature "
			+ " from beacon_history b "
			+ " inner join silo_history s on s.id = b.silo_history_id "
			+ " where date(s.closed_at) between date(:starDate) and date(:endDate) "
			+ " and b.distance is null "
			+ "	and s.silo_id = :siloId "
			+ " group by date(b.updated_at);", nativeQuery = true)
	List<Object[]> generateGraphicFor(@Param("siloId") Long siloId, @Param("starDate") Date startDate, 
			@Param("endDate") Date endDate);
	
	void delete(SiloHistory siloHistory);
}
