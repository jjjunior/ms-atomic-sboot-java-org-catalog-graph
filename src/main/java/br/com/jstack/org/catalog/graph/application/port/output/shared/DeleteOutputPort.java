package br.com.jstack.org.catalog.graph.application.port.output.shared;

public interface DeleteOutputPort<N, ID> {
	void deleteById(ID id);
}