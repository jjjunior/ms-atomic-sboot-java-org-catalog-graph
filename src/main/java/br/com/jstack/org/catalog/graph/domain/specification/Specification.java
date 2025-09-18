package br.com.jstack.org.catalog.graph.domain.specification;

import java.util.function.Consumer;
import java.util.function.Supplier;

@FunctionalInterface
public interface Specification<T> {
	boolean isSatisfiedBy(T candidate);
	
	default Specification<T> and(Specification<T> other) {
		return c -> this.isSatisfiedBy(c) && other.isSatisfiedBy(c);
	}
	
	default Specification<T> or(Specification<T> other) {
		return c -> this.isSatisfiedBy(c) || other.isSatisfiedBy(c);
	}
	
	default Specification<T> not() {
		return c -> !this.isSatisfiedBy(c);
	}
	
	default Consumer<T> toPolicy(Supplier<? extends RuntimeException> ex) {
		return target -> {
			if (!isSatisfiedBy(target)) throw ex.get();
		};
	}
}