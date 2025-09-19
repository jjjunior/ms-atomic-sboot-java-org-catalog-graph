package br.com.jstack.org.catalog.graph.domain.specification;

import java.util.function.BiFunction;
import java.util.function.Function;

public class UniqueNameExcludingSelfSpec<T> implements Specification<T> {
	
	private final BiFunction<String, Long, Boolean> existsByNameExcludingId;
	private final Function<T, String>               nameExtractor;
	private final Function<T, Long>                 idExtractor;
	
	public UniqueNameExcludingSelfSpec(BiFunction<String, Long, Boolean> existsByNameExcludingId,
	                                   Function<T, String> nameExtractor,
	                                   Function<T, Long> idExtractor) {
		this.existsByNameExcludingId = existsByNameExcludingId;
		this.nameExtractor           = nameExtractor;
		this.idExtractor             = idExtractor;
	}
	
	@Override
	public boolean isSatisfiedBy(T candidate) {
		String name = nameExtractor.apply(candidate);
		Long   id   = idExtractor.apply(candidate);
		return !existsByNameExcludingId.apply(name, id);
	}
}