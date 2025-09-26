package br.com.jstack.org.catalog.graph.domain.policy.shared;

import java.util.ArrayList;
import java.util.List;

import br.com.jstack.org.catalog.graph.domain.vo.OperationType;

public final class CompositePolicy<T> implements ValidationPolicy<T> {
	private final Class<T>                  targetType;
	private final List<ValidationPolicy<T>> children = new ArrayList<>();
	
	public CompositePolicy(Class<T> targetType) {
		this.targetType = targetType;
	}
	
	public CompositePolicy<T> add(ValidationPolicy<T> p) {
		children.add(p);
		return this;
	}
	
	@Override
	public boolean supports(OperationType op) {
		return true;
	} // delega por child
	
	@Override
	public void validate(T target, OperationType op) {
		for (var p : children) if (p.supports(op)) p.validate(target, op);
	}
	
	@Override
	public Class<T> getTargetType() {
		return targetType;
	}
}
