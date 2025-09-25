package br.com.jstack.org.catalog.graph.application.port.input.shared;

import java.util.List;

public interface RetrieveAllUseCase<T> {
	List<T> retrieveAll();
}