package br.com.jstack.org.catalog.graph.application.port.output.shared;

import java.util.List;

public interface LoadOutputPort<N, ID> {
	N findById(ID id);
	
	List<N> findAll();
}