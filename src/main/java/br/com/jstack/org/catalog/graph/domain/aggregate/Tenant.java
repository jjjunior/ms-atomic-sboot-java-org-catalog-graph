package br.com.jstack.org.catalog.graph.domain.aggregate;

import java.util.Map;
import java.util.Set;

import br.com.jstack.org.catalog.graph.domain.aggregate.shared.CatalogAggregate;
import br.com.jstack.org.catalog.graph.framework.adapter.output.node.DomainNode;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import static br.com.jstack.org.catalog.graph.domain.vo.Lifecycle.ACTIVE;

/**
 * Aggregate Root: Tenant
 */
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public final class Tenant extends CatalogAggregate<String> {
	
	private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();
	
	@Size(min = 2, max = 80, message = "tenant.owner.size")
	private String owner;
	
	private Set<DomainNode> domains;
	
	public static Tenant create(String id, String name, String owner, Map<String, String> labels, Map<String, String> annotations) {
		Tenant t = Tenant.builder().name(id).name(name).owner(owner).labels(labels).annotations(annotations).lifecycle(ACTIVE).build();
		t.validate();
		return t;
	}
	
	public void update(String name, String owner, String description, Map<String, String> labels, Map<String, String> annotations) {
		this.name        = name;
		this.owner       = owner;
		this.description = description;
		this.labels      = labels;
		this.annotations = annotations;
		validate();
	}
	
	private void validate() {
		assertBaseInvariants();
		var violations = VALIDATOR.validate(this);
		if (!violations.isEmpty()) throw new ConstraintViolationException(violations);
		// Regras locais simples
		if (description != null) {
			int len = description.trim().length();
			if (len < 3 || len > 255) throw new IllegalArgumentException("metadata.description.size");
		}
	}
}