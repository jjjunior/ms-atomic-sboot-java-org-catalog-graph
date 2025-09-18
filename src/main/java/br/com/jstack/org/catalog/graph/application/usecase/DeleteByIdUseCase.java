package br.com.jstack.org.catalog.graph.application.usecase;

public interface DeleteByIdUseCase<T, ID> {
	void deleteById(ID uuid);
}