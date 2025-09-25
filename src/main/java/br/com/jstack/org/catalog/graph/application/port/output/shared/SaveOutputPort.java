package br.com.jstack.org.catalog.graph.application.port.output.shared;

public interface SaveOutputPort<N, ID> {
	N update(N node);
	
	N insert(N node);
}