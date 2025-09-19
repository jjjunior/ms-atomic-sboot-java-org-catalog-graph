package br.com.jstack.org.catalog.graph.application.port.output;

import br.com.jstack.org.catalog.graph.domain.model.BusinessDomain;

public interface BusinessDomainOutputPort extends PersistencePort<BusinessDomain, String> {
	boolean existsByTenantAndAcronym(String tenantId, String acronym);
}
