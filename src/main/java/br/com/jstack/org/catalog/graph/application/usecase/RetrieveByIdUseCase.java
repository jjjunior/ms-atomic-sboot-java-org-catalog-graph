package br.com.jstack.org.catalog.graph.application.usecase;

public interface RetrieveByIdUseCase<T, ID> {
	T retrieveById(ID uuid);
}