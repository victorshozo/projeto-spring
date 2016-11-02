package com.graincare.silos;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

@RepositoryDefinition(domainClass = SiloHistory.class, idClass = Long.class)
public interface SiloHistoryRepository {

	List<SiloHistory> findAll();
	
	List<SiloHistory> findAllByOpenFalse();

	Optional<SiloHistory> findBySiloIdAndOpenFalse(Long siloId);
	
	List<SiloHistory> findBySiloIdAndOpenTrueAndClosedAtGreaterThan(Long siloId, Calendar closedAt);

	SiloHistory save(SiloHistory siloHistory);
	
	@Query(value = "select date(s.closed_at) closedAt, "
			+ " date(s.opened_at) openAt, "
			+ " s.grao grao, "
			+ " sum(b.`temperature`) / sum(1) as averageTemperature, "
			+ " sum(b.`humidity`) / sum(1) as averageHumidity, "
			+ " (select max(bh.distance) from beacon_history bh "
			+ "		where bh.silo_history_id = s.id "
			+ "			and bh.humidity is null"
			+ "			and bh.temperature is null"
			+ "			group by bh.silo_history_id) as capacity "
			+ " from silo_history s "
			+ " inner join beacon_history b on b.silo_history_id = s.id "
			+ " where s.open = true "
			+ " and b.humidity is not null "
			+ " and b.temperature is not null "
			+ " and s.closed_at >= DATE_SUB(curdate(), INTERVAL 3 MONTH) "
			+ " and s.silo_id = :siloId"
			+ " group by s.id", nativeQuery = true)
	List<Object[]> generateReportFor(@Param("siloId") Long siloId);

}
