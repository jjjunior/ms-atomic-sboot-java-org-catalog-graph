package br.com.jstack.org.catalog.graph.application.port.input.shared;

public interface UpdateUseCase<T> {
	T update(T domain);
}