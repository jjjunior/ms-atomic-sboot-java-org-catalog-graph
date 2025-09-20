package br.com.jstack.org.catalog.graph.domain.model;

import java.time.LocalDateTime;
import java.util.Set;

import br.com.jstack.org.catalog.graph.domain.node.BusinessDomainNode;
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
	@With
	String        tenantId,
	String        name,
	TenantStatus  status,
	String        createdBy,
	@With
	String        updatedBy,
	LocalDateTime createdAt,
	@With
	LocalDateTime updatedAt,
	Set<BusinessDomainNode> businessDomains
	
) {
	
	public static TenantCompany create(TenantCompany tenantCompany, ValidationPolicy<TenantCompany> policy) {
		var tenant = tenantCompany.toBuilder()
			.status(ACTIVE)
			.build();
		
		policy.validate(tenant,CREATE);
		return tenant;
	}
	
	/** Rename command with audit. */
	public TenantCompany rename(String newName, ValidationPolicy<TenantCompany> policy) {
		var tenant = this.toBuilder()
			.name(newName)
			.build();
		policy.validate(tenant, UPDATE);
		return tenant;
	}
	
	/** Inactivate tenant. */
	public TenantCompany inactivate(ValidationPolicy<TenantCompany> policy) {
		var tenant = this.toBuilder()
			.status(INACTIVE)
			.build();
		
		policy.validate(tenant, UPDATE);
		return tenant;
	}
	
	/** Activate tenant. */
	public TenantCompany activate(ValidationPolicy<TenantCompany> policy) {
		var tenant = this.toBuilder()
			.status(ACTIVE)
			.build();
		
		policy.validate(tenant, UPDATE);
		return tenant;
	}
}