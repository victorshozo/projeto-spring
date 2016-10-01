package com.graincare.graos;

import java.util.List;

import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Grao.class, idClass = Long.class)
public interface GraoRepository {

	List<Grao> findAll();

}