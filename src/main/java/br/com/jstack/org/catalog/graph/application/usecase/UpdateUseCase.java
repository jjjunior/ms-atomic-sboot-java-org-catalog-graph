package br.com.jstack.org.catalog.graph.application.usecase;

public interface UpdateUseCase<T> {
	T update(T domain);
}