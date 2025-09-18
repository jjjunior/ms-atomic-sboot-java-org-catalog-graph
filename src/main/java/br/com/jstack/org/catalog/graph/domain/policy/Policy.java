package br.com.jstack.org.catalog.graph.domain.policy;

@FunctionalInterface
public interface Policy<T> {
	static <T> Policy<T> compose(Policy<T>... ps) {
		return t -> {
			for (var p : ps) p.validate(t);
		};
	}
	
	void validate(T target);
}