package br.com.jstack.org.catalog.graph.application.port.output;

import br.com.jstack.org.catalog.graph.domain.aggregate.Tenant;

public interface TenantOutputPort extends PersistencePort<Tenant, String> {
	Boolean existsByTenantId(String tenantId);
}
