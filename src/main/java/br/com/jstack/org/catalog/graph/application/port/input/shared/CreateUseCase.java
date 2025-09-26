package br.com.jstack.org.catalog.graph.application.port.input.shared;

public interface CreateUseCase<T> {
	T create(T domain);
}
