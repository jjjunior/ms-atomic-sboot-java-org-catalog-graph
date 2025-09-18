package br.com.jstack.org.catalog.graph.domain.policy;

import br.com.jstack.org.catalog.graph.domain.vo.OperationType;

public interface ValidationPolicy<T> {
	
	boolean supports(OperationType operation);
	
	void validate(T target, OperationType operation);
	
	Class<T> getTargetType();
}