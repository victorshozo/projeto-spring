package com.graincare.silos;

import java.util.List;

import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Silo.class, idClass = Long.class)
public interface SiloRepository {

	List<Silo> findAll();

}
