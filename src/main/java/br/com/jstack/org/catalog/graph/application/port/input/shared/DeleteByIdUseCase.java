package br.com.jstack.org.catalog.graph.application.port.input.shared;

public interface DeleteByIdUseCase<T, ID> {
	void deleteById(ID uuid);
}