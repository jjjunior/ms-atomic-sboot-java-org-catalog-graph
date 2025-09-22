package br.com.jstack.org.catalog.graph.domain.policy;

import br.com.jstack.org.catalog.graph.application.port.output.TenantOutputPort;
import br.com.jstack.org.catalog.graph.domain.aggregate.TenantAggregate;
import br.com.jstack.org.catalog.graph.domain.specification.SpecificationFactory;
import br.com.jstack.org.catalog.graph.domain.vo.OperationType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TenantPolicy implements ValidationPolicy<TenantAggregate> {
	
	private final SpecificationFactory factory;
	private final TenantOutputPort     outputPort;
	
	@Override
	public void validate(TenantAggregate tenantAggregate, OperationType operation) {
		
		if (operation == OperationType.CREATE) {
			var uniqueNameSpec = factory.uniqueName(outputPort::existsByTenantId, TenantAggregate::getTenantId);
			if (!uniqueNameSpec.isSatisfiedBy(tenantAggregate)) {
				throw new IllegalArgumentException("Tenant Company must be unique (tenantId).");
			}
		}
//
//		if (operation == OperationType.UPDATE) {
//			Specification<BusinessDomain> uniqueNameExclIdSpec = specFactory.uniqueNameExcludingSelf(outputPort::existsByNameAndIdNot, BusinessDomain::getName, BusinessDomain::getId);
//			if (!uniqueNameExclIdSpec.isSatisfiedBy(domain)) {
//				throw new IllegalArgumentException("Business Domain Name must be unique (excluding self).");
//			}
//		}
	}
	
	@Override
	public boolean supports(OperationType operation) {
		return operation == OperationType.CREATE || operation == OperationType.UPDATE;
	}
	
	@Override
	public Class<TenantAggregate> getTargetType() {
		return TenantAggregate.class;
	}
}