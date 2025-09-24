package br.com.jstack.org.catalog.graph.application.port.output;

import br.com.jstack.org.catalog.graph.domain.aggregate.Domain;

public interface DomainOutputPort extends PersistencePort<Domain, String> {
	boolean existsByTenantAndAcronym(String tenantId, String acronym);
}
