package br.com.jstack.org.catalog.graph.application.port.input;

import br.com.jstack.org.catalog.graph.application.port.input.shared.CreateUseCase;
import br.com.jstack.org.catalog.graph.application.port.input.shared.DeleteByIdUseCase;
import br.com.jstack.org.catalog.graph.application.port.input.shared.RetrieveAllUseCase;
import br.com.jstack.org.catalog.graph.application.port.input.shared.RetrieveByIdUseCase;
import br.com.jstack.org.catalog.graph.application.port.input.shared.UpdateUseCase;
import br.com.jstack.org.catalog.graph.domain.aggregate.Domain;

public interface DomainUseCase extends
	CreateUseCase<Domain>,
	UpdateUseCase<Domain>,
	RetrieveAllUseCase<Domain>,
	RetrieveByIdUseCase<Domain, String>,
	DeleteByIdUseCase<Domain, String> {
}
