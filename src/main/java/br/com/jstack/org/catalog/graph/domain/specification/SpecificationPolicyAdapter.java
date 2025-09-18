package br.com.jstack.org.catalog.graph.domain.specification;

import java.util.function.Supplier;

import br.com.jstack.org.catalog.graph.domain.policy.Policy;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class SpecificationPolicyAdapter {
	public static <T> Policy<T> from(Specification<T> spec, Supplier<? extends RuntimeException> ex) {
		return target -> { if (!spec.isSatisfiedBy(target)) throw ex.get(); };
	}
}