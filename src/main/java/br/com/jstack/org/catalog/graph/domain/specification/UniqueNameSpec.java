package br.com.jstack.org.catalog.graph.domain.specification;

import java.util.function.Function;

import lombok.Builder;

@Builder(toBuilder = true)
public record UniqueNameSpec<T>(
	Function<String, Boolean> existsByName,
	Function<T, String> nameExtractor
) implements Specification<T> {
	
	@Override
	public boolean isSatisfiedBy(T candidate) {
		String name = nameExtractor.apply(candidate);
		return !existsByName.apply(name);
	}
}