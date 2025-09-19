package br.com.jstack.org.catalog.graph.domain.policy;

import br.com.jstack.org.catalog.graph.application.port.output.BusinessDomainOutputPort;
import br.com.jstack.org.catalog.graph.domain.model.BusinessDomain;
import br.com.jstack.org.catalog.graph.domain.specification.SpecificationFactory;
import br.com.jstack.org.catalog.graph.domain.vo.OperationType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BusinessDomainPolicy implements ValidationPolicy<BusinessDomain> {
	
	private final SpecificationFactory     specFactory;
	private final BusinessDomainOutputPort outputPort;
	
	@Override
	public void validate(BusinessDomain domain, OperationType operation) {
		
//		if (operation == OperationType.CREATE) {
//			Specification<BusinessDomain> uniqueNameSpec = specFactory.uniqueName(outputPort::existsByName, BusinessDomain::getName);
//			if (!uniqueNameSpec.isSatisfiedBy(domain)) {
//				throw new IllegalArgumentException("Business Domain Name must be unique.");
//			}
//		}
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
	public Class<BusinessDomain> getTargetType() {
		return BusinessDomain.class;
	}
}