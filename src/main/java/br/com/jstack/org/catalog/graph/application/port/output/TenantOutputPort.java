package br.com.jstack.org.catalog.graph.application.port.output;

import br.com.jstack.org.catalog.graph.domain.aggregate.TenantAggregate;

public interface TenantOutputPort extends PersistencePort<TenantAggregate, String> {
	Boolean existsByTenantId(String tenantId);
}
