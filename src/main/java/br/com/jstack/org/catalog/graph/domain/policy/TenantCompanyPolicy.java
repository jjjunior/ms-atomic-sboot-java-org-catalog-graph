package br.com.jstack.org.catalog.graph.domain.policy;

import br.com.jstack.org.catalog.graph.application.port.output.TenantCompanyOutputPort;
import br.com.jstack.org.catalog.graph.domain.model.TenantCompany;
import br.com.jstack.org.catalog.graph.domain.specification.Specification;
import br.com.jstack.org.catalog.graph.domain.specification.SpecificationFactory;
import br.com.jstack.org.catalog.graph.domain.vo.OperationType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TenantCompanyPolicy implements ValidationPolicy<TenantCompany> {
	
	private final SpecificationFactory    specFactory;
	private final TenantCompanyOutputPort outputPort;
	
	@Override
	public void validate(TenantCompany tenantCompany, OperationType operation) {
		
		if (operation == OperationType.CREATE) {
			Specification<TenantCompany> uniqueNameSpec = specFactory.uniqueName(outputPort::existsByTenantId, TenantCompany::tenantId);
			if (!uniqueNameSpec.isSatisfiedBy(tenantCompany)) {
				throw new IllegalArgumentException("Tenant Company must be unique.");
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
	public Class<TenantCompany> getTargetType() {
		return TenantCompany.class;
	}
}