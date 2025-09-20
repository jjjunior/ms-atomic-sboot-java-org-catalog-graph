package br.com.jstack.org.catalog.graph.domain.specification;

import java.util.function.BiFunction;
import java.util.function.Function;


public record UniqueNameExcludingSelfSpec<T>(
	BiFunction<String, Long, Boolean> existsByNameExcludingId,
	Function<T, String> nameExtractor,
	Function<T, Long> idExtractor
) implements Specification<T> {
	
	@Override
	public boolean isSatisfiedBy(T candidate) {
		String name = nameExtractor.apply(candidate);
		Long   id   = idExtractor.apply(candidate);
		return !existsByNameExcludingId.apply(name, id);
	}
}