package br.com.jstack.org.catalog.graph.application.port.output;

import br.com.jstack.org.catalog.graph.application.port.output.shared.DeleteOutputPort;
import br.com.jstack.org.catalog.graph.application.port.output.shared.LoadOutputPort;
import br.com.jstack.org.catalog.graph.application.port.output.shared.SaveOutputPort;
import br.com.jstack.org.catalog.graph.domain.aggregate.Tenant;

public interface TenantOutputPort extends
	LoadOutputPort<Tenant, String>,
	SaveOutputPort<Tenant, String>,
	DeleteOutputPort<Tenant, String> {
	Boolean existsByTenantId(String tenantId);
}
