package br.com.jstack.org.catalog.graph.application.port.input;

import java.util.ArrayList;
import java.util.List;

import br.com.jstack.org.catalog.graph.application.port.output.DomainOutputPort;
import br.com.jstack.org.catalog.graph.application.usecase.CreateUseCase;
import br.com.jstack.org.catalog.graph.application.usecase.DeleteByIdUseCase;
import br.com.jstack.org.catalog.graph.application.usecase.RetrieveAllUseCase;
import br.com.jstack.org.catalog.graph.application.usecase.RetrieveByIdUseCase;
import br.com.jstack.org.catalog.graph.application.usecase.UpdateUseCase;
import br.com.jstack.org.catalog.graph.domain.aggregate.Domain;
import br.com.jstack.org.catalog.graph.domain.policy.PolicyResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static br.com.jstack.org.catalog.graph.domain.vo.OperationType.CREATE;
import static br.com.jstack.org.catalog.graph.domain.vo.OperationType.UPDATE;

@Component
@RequiredArgsConstructor
public class DomainInputPort implements
	CreateUseCase<Domain>,
	RetrieveByIdUseCase<Domain, String>,
	RetrieveAllUseCase<Domain>,
	UpdateUseCase<Domain>,
	DeleteByIdUseCase<Domain, String> {
	
	private final DomainOutputPort       outputPort;
	private final PolicyResolver<Domain> policyResolver;
	
	@Override
	public Domain create(Domain businessDomain) {
		Domain domain = Domain.create(businessDomain, policyResolver.resolve(CREATE, Domain.class));
		return outputPort.save(domain);
	}
	
	@Override
	public Domain retrieveById(String id) {
		return outputPort.findById(id);
	}
	
	@Override
	public List<Domain> retrieveAll() {
		return new ArrayList<>(outputPort.findAll());
	}
	
	@Override
	public Domain update(Domain businessDomain) {
		Domain domain = businessDomain.update(businessDomain, policyResolver.resolve(UPDATE, Domain.class));
		return outputPort.update(domain);
	}
	
	@Override
	public void deleteById(String id) {
		outputPort.deleteById(id);
	}
}
