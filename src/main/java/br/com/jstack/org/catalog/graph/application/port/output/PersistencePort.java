package br.com.jstack.org.catalog.graph.application.port.output;

import java.util.List;

public interface PersistencePort<N, ID> {
	N save(N node);
	
	N findById(ID id);
	
	List<N> findAll();
	
	void deleteById(ID id);
	
	N update(N node);
}