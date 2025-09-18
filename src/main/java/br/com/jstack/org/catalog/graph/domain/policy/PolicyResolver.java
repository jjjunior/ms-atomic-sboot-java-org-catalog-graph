package br.com.jstack.org.catalog.graph.domain.policy;

import java.util.List;

import br.com.jstack.org.catalog.graph.domain.vo.OperationType;
import org.springframework.stereotype.Component;

@Component
public class PolicyResolver<T> {
	
	private final List<ValidationPolicy<T>> policies;
	
	public PolicyResolver(List<ValidationPolicy<T>> policies) {
		this.policies = policies;
	}
	
	public <T> ValidationPolicy<T> resolve(OperationType operation, Class<T> clazz) {
		return (ValidationPolicy<T>) policies.stream()
			.filter(p -> p.supports(operation))
			.filter(p -> p.getTargetType().equals(clazz))
			.findFirst()
			.orElseThrow(() -> new IllegalStateException("No policy found for operation: " + operation + " and type: " + clazz.getSimpleName()));
	}
}