package br.com.jstack.org.catalog.graph.application.port.output;

import br.com.jstack.org.catalog.graph.application.port.output.shared.DeleteOutputPort;
import br.com.jstack.org.catalog.graph.application.port.output.shared.LoadOutputPort;
import br.com.jstack.org.catalog.graph.application.port.output.shared.SaveOutputPort;
import br.com.jstack.org.catalog.graph.domain.aggregate.Domain;

public interface DomainOutputPort extends
	LoadOutputPort<Domain, String>,
	SaveOutputPort<Domain, String>,
	DeleteOutputPort<Domain, String> {
	boolean existsByTenantAndAcronym(String tenantId, String acronym);
}
