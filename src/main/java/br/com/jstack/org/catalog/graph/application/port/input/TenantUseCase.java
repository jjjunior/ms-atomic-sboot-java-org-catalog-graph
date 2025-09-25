package br.com.jstack.org.catalog.graph.application.port.input;

import br.com.jstack.org.catalog.graph.application.port.input.shared.CreateUseCase;
import br.com.jstack.org.catalog.graph.application.port.input.shared.DeleteByIdUseCase;
import br.com.jstack.org.catalog.graph.application.port.input.shared.RetrieveAllUseCase;
import br.com.jstack.org.catalog.graph.application.port.input.shared.RetrieveByIdUseCase;
import br.com.jstack.org.catalog.graph.application.port.input.shared.UpdateUseCase;
import br.com.jstack.org.catalog.graph.domain.aggregate.Tenant;

public interface TenantUseCase extends
	CreateUseCase<Tenant>,
	UpdateUseCase<Tenant>,
	RetrieveAllUseCase<Tenant>,
	RetrieveByIdUseCase<Tenant, String>,
	DeleteByIdUseCase<Tenant, String> {
}
