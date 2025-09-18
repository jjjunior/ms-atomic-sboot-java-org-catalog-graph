package br.com.jstack.org.catalog.graph.application.port.output;

import java.util.List;
import java.util.Optional;

public interface PersistencePort<N, ID> {
	N save(N node);
	
	Optional<N> findById(ID id);
	
	List<N> findAll();
	
	void deleteById(ID id);
	
	N update(N node);
}