package br.com.jstack.org.catalog.graph.application.port.output;

import br.com.jstack.org.catalog.graph.domain.model.TenantCompany;

public interface TenantCompanyOutputPort extends PersistencePort<TenantCompany, String> {
	Boolean existsByTenantId(String tenantId);
}
