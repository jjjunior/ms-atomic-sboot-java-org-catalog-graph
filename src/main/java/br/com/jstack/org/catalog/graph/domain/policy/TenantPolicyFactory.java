package br.com.jstack.org.catalog.graph.domain.policy;

import br.com.jstack.org.catalog.graph.application.port.output.TenantOutputPort;
import br.com.jstack.org.catalog.graph.domain.aggregate.Tenant;
import br.com.jstack.org.catalog.graph.domain.policy.shared.CompositePolicy;
import br.com.jstack.org.catalog.graph.domain.policy.shared.SpecificationPolicyAdapter;
import br.com.jstack.org.catalog.graph.domain.policy.shared.ValidationPolicy;
import br.com.jstack.org.catalog.graph.domain.specification.Specification;
import br.com.jstack.org.catalog.graph.domain.specification.SpecificationFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static br.com.jstack.org.catalog.graph.domain.vo.OperationType.CREATE;

@Component
@RequiredArgsConstructor
public class TenantPolicyFactory {
	
	private final SpecificationFactory specs;
	private final TenantOutputPort     tenantOut;
	
	public ValidationPolicy<Tenant> createPolicy() {
		
		Specification<Tenant> uniqueId = specs.uniqueName(tenantOut::existsByTenantId, Tenant::getName);
		var uniqueIdPolicy = SpecificationPolicyAdapter.of(Tenant.class, uniqueId,
			() -> new IllegalArgumentException("Tenant name must be unique."), CREATE);
		
		return new CompositePolicy<>(Tenant.class).add(uniqueIdPolicy);
	}
	
	public ValidationPolicy<Tenant> updatePolicy() {
		return new CompositePolicy<>(Tenant.class);
	}
}