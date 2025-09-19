package br.com.jstack.org.catalog.graph.application.usecase;

import java.util.List;

public interface RetrieveAllUseCase<T> {
	List<T> retrieveAll();
}