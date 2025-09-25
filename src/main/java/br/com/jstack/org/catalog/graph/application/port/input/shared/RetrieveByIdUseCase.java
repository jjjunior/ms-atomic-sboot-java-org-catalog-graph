package br.com.jstack.org.catalog.graph.application.port.input.shared;

public interface RetrieveByIdUseCase<T, ID> {
	T retrieveById(ID uuid);
}