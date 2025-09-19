package br.com.jstack.org.catalog.graph.application.usecase;

public interface CreateUseCase<T> {
	T create(T domain);
}
