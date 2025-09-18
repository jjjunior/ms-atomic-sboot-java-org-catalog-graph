package br.com.jstack.org.catalog.graph.domain.policy;

import br.com.jstack.org.catalog.graph.application.port.output.BusinessDomainOutputPort;
import br.com.jstack.org.catalog.graph.domain.model.BusinessDomain;
import br.com.jstack.org.catalog.graph.framework.adapter.exception.DomainRuleViolation;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class UniqueAcronymPerTenantPolicy implements Policy<BusinessDomain> {
	
	private BusinessDomainOutputPort businessDomainOutputPort;
	
	@Override
	public void validate(BusinessDomain businessDomain) {
		if (businessDomainOutputPort.existsByTenantAndAcronym(businessDomain.getTenantId(), businessDomain.getAcronym())) {
			throw new DomainRuleViolation("Acronym must be unique within the tenant.");
		}
	}
}