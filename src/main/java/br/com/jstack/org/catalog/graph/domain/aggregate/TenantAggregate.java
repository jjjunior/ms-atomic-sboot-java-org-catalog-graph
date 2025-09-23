package br.com.jstack.org.catalog.graph.domain.aggregate;

import java.time.LocalDateTime;
import java.util.Set;

import br.com.jstack.org.catalog.graph.domain.policy.ValidationPolicy;
import br.com.jstack.org.catalog.graph.domain.vo.TenantStatus;
import br.com.jstack.org.catalog.graph.framework.adapter.output.node.DomainNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import static br.com.jstack.org.catalog.graph.domain.vo.OperationType.CREATE;
import static br.com.jstack.org.catalog.graph.domain.vo.OperationType.UPDATE;
import static br.com.jstack.org.catalog.graph.domain.vo.TenantStatus.ACTIVE;
import static br.com.jstack.org.catalog.graph.domain.vo.TenantStatus.INACTIVE;

/**
 * Aggregate Root: TenantCompany
 * <p>
 * Domain class representing a tenant/company.
 * Uses explicit commands (activate/inactivate/updateName), each returning
 * a NEW instance.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "tenantId")
@Builder(toBuilder = true)
public class TenantAggregate {
	private String          tenantId;
	private String          name;
	private TenantStatus    status;
	private String          createdBy;
	private String          updatedBy;
	private LocalDateTime   createdAt;
	private LocalDateTime   updatedAt;
	private Set<DomainNode> businessDomains;
	
	
	public static TenantAggregate create(TenantAggregate tenantAggregateCompany, ValidationPolicy<TenantAggregate> policy) {
		var tenant = tenantAggregateCompany.toBuilder()
			.status(ACTIVE)
			.build();
		
		policy.validate(tenant, CREATE);
		return tenant;
	}
	
	/**
	 * Rename command with audit.
	 */
	public TenantAggregate rename(String newName, ValidationPolicy<TenantAggregate> policy) {
		var tenant = this.toBuilder()
			.name(newName)
			.build();
		policy.validate(tenant, UPDATE);
		return tenant;
	}
	
	/**
	 * Inactivate tenant.
	 */
	public TenantAggregate inactivate(ValidationPolicy<TenantAggregate> policy) {
		var tenant = this.toBuilder()
			.status(INACTIVE)
			.build();
		
		policy.validate(tenant, UPDATE);
		return tenant;
	}
	
	/**
	 * Activate tenant.
	 */
	public TenantAggregate activate(ValidationPolicy<TenantAggregate> policy) {
		var tenant = this.toBuilder()
			.status(ACTIVE)
			.build();
		
		policy.validate(tenant, UPDATE);
		return tenant;
	}
}