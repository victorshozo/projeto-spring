package com.graincare.beacon;

import java.util.List;

import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = BeaconHistory.class, idClass = Long.class)
public interface BeaconHistoryRepository {

	List<BeaconHistory> findAll();

}
