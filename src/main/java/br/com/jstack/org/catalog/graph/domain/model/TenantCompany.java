package br.com.jstack.org.catalog.graph.domain.model;

import java.time.LocalDateTime;

import br.com.jstack.org.catalog.graph.domain.policy.ValidationPolicy;
import br.com.jstack.org.catalog.graph.domain.vo.TenantStatus;
import lombok.Builder;
import lombok.With;

import static br.com.jstack.org.catalog.graph.domain.vo.OperationType.CREATE;
import static br.com.jstack.org.catalog.graph.domain.vo.OperationType.UPDATE;
import static br.com.jstack.org.catalog.graph.domain.vo.TenantStatus.ACTIVE;
import static br.com.jstack.org.catalog.graph.domain.vo.TenantStatus.INACTIVE;

/**
 * Aggregate Root: TenantCompany
 *
 * Immutable domain record representing a tenant/company.
 * Uses explicit commands (activate/inactivate/updateName), each returning
 * a NEW instance (immutability).
 */
@Builder(toBuilder = true)
public record TenantCompany(
	String        tenantId,
	String        name,
	TenantStatus  status,
	String        createdBy,
	@With
	String        updatedBy,
	LocalDateTime createdAt,
	@With
	LocalDateTime updatedAt
	
) {
	
	public static TenantCompany create(TenantCompany draft, ValidationPolicy<TenantCompany> policy) {
		var tenant = draft.toBuilder()
			.status(ACTIVE)
			.build();
		
		policy.validate(tenant,CREATE);
		return tenant;
	}
	
	/** Rename command with audit. */
	public TenantCompany updateName(String newName, ValidationPolicy<TenantCompany> policy) {
		var next = this.toBuilder()
			.name(newName)
			.build();
		policy.validate(next, UPDATE);
		return next;
	}
	
	/** Inactivate tenant. */
	public TenantCompany inactivate(ValidationPolicy<TenantCompany> policy) {
		var next = this.toBuilder()
			.status(INACTIVE)
			.build();
		
		policy.validate(next, UPDATE);
		return next;
	}
	
	/** Activate tenant. */
	public TenantCompany activate(ValidationPolicy<TenantCompany> policy) {
		var next = this.toBuilder()
			.status(ACTIVE)
			.build();
		
		policy.validate(next, UPDATE);
		return next;
	}
}