package br.com.jstack.org.catalog.graph.domain.policy.shared;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import br.com.jstack.org.catalog.graph.domain.vo.OperationType;
import org.springframework.stereotype.Component;

@Component
public class PolicyResolver {
	
	private final Map<Key, List<ValidationPolicy<?>>> index = new ConcurrentHashMap<>();
	
	public PolicyResolver(List<ValidationPolicy<?>> policies) {
		for (var p : policies) {
			for (var op : OperationType.values()) {
				if (p.supports(op)) {
					index.computeIfAbsent(new Key(p.getTargetType(), op), k -> new ArrayList<>()).add(p);
				}
			}
		}
		index.replaceAll((k, v) -> List.copyOf(v));
	}
	
	public <T> void validate(OperationType op, T target, Class<T> targetType) {
		var policies = index.getOrDefault(new Key(targetType, op), List.of());
		if (policies.isEmpty()) return; // sem policy registrada = sem validação
		for (var p : policies) {
			((ValidationPolicy<T>) p).validate(target, op);
		}
	}
	
	public <T> List<ValidationPolicy<T>> getPolicies(OperationType op, Class<T> targetType) {
		return (List) index.getOrDefault(new Key(targetType, op), List.of());
	}
	
	private record Key(
		Class<?> target,
		OperationType op
	) {
	}
}