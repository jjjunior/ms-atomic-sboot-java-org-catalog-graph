package br.com.jstack.org.catalog.graph.application.port.output;

import br.com.jstack.org.catalog.graph.domain.aggregate.DomainAggregate;

public interface DomainOutputPort extends PersistencePort<DomainAggregate, String> {
	boolean existsByTenantAndAcronym(String tenantId, String acronym);
}
