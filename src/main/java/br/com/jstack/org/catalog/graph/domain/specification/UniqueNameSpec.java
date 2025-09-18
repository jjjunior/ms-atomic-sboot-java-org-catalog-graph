package br.com.jstack.org.catalog.graph.domain.specification;

import java.util.function.Function;

public class UniqueNameSpec<T> implements Specification<T> {
	
	private final Function<String, Boolean> existsByName;
	private final Function<T, String>       nameExtractor;
	
	public UniqueNameSpec(Function<String, Boolean> existsByName, Function<T, String> nameExtractor) {
		this.existsByName  = existsByName;
		this.nameExtractor = nameExtractor;
	}
	
	@Override
	public boolean isSatisfiedBy(T candidate) {
		String name = nameExtractor.apply(candidate);
		return !existsByName.apply(name);
	}
}