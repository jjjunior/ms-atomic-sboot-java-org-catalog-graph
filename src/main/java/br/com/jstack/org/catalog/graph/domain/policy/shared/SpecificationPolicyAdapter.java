package br.com.jstack.org.catalog.graph.domain.policy.shared;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

import br.com.jstack.org.catalog.graph.domain.specification.Specification;
import br.com.jstack.org.catalog.graph.domain.vo.OperationType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class SpecificationPolicyAdapter<T> implements ValidationPolicy<T> {
	
	Class<T>                             targetType;
	Set<OperationType>                   operations;
	Specification<T>                     specification;
	Supplier<? extends RuntimeException> exceptionSupplier;
	
	public static <T> SpecificationPolicyAdapter<T> of(
		Class<T> targetType,
		Specification<T> spec,
		Supplier<? extends RuntimeException> exceptionSupplier,
		OperationType... supported
	) {
		return new SpecificationPolicyAdapter<>(
			Objects.requireNonNull(targetType),
			supported == null || supported.length == 0
				? EnumSet.allOf(OperationType.class)
				: EnumSet.of(supported[0], supported),
			Objects.requireNonNull(spec),
			Objects.requireNonNull(exceptionSupplier)
		);
	}
	
	@Override
	public boolean supports(OperationType operation) {
		return operations.contains(operation);
	}
	
	@Override
	public void validate(T target, OperationType operation) {
		if (!specification.isSatisfiedBy(target)) {
			throw exceptionSupplier.get();
		}
	}
}