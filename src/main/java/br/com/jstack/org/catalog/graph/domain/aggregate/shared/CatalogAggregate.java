package br.com.jstack.org.catalog.graph.domain.aggregate.shared;

import java.time.LocalDateTime;
import java.util.Map;

import br.com.jstack.org.catalog.graph.domain.vo.Lifecycle;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class CatalogAggregate<ID> implements AggregateRoot<ID> {
	
	@NotBlank(message = "tenant.displayName.notblank")
	@Size(min = 2, max = 40, message = "tenant.displayName.size")
	protected ID                  name;
	protected String              kind;
	protected String              description;
	protected Map<String, String> labels;
	protected Map<String, String> annotations;
	protected Lifecycle           lifecycle;
	protected String              createdBy;
	protected LocalDateTime       createdAt;
	protected String              updatedBy;
	protected LocalDateTime       updatedAt;
	
	public void updateMetadata(String description, Map<String, String> labels, Map<String, String> annotations) {
		this.description = description;
		this.labels      = labels;
		this.annotations = annotations;
	}
	
	public void deactivate() {
		ensureNotDeprecated();
		this.lifecycle = Lifecycle.INACTIVE;
	}
	
	public void deprecate() {
		this.lifecycle = Lifecycle.DEPRECATED;
	}
	
	protected void assertBaseInvariants() {
		if (name == null) throw new IllegalArgumentException("aggregate.name.required");
	}
	
	protected void ensureNotDeprecated() {
		if (this.lifecycle == Lifecycle.DEPRECATED)
			throw new IllegalStateException("aggregate.transition.fromDeprecated.notAllowed");
	}
}