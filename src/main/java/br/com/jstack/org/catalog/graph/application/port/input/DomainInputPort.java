package br.com.jstack.org.catalog.graph.application.port.input;

import java.util.List;
import java.util.stream.Collectors;

import br.com.jstack.org.catalog.graph.application.port.output.DomainOutputPort;
import br.com.jstack.org.catalog.graph.application.usecase.CreateUseCase;
import br.com.jstack.org.catalog.graph.application.usecase.DeleteByIdUseCase;
import br.com.jstack.org.catalog.graph.application.usecase.RetrieveAllUseCase;
import br.com.jstack.org.catalog.graph.application.usecase.RetrieveByIdUseCase;
import br.com.jstack.org.catalog.graph.application.usecase.UpdateUseCase;
import br.com.jstack.org.catalog.graph.domain.aggregate.DomainAggregate;
import br.com.jstack.org.catalog.graph.domain.policy.PolicyResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static br.com.jstack.org.catalog.graph.domain.vo.OperationType.CREATE;
import static br.com.jstack.org.catalog.graph.domain.vo.OperationType.UPDATE;

@Component
@RequiredArgsConstructor
public class DomainInputPort implements
	CreateUseCase<DomainAggregate>,
	RetrieveByIdUseCase<DomainAggregate, String>,
	RetrieveAllUseCase<DomainAggregate>,
	UpdateUseCase<DomainAggregate>,
	DeleteByIdUseCase<DomainAggregate, String> {
	
	private final DomainOutputPort                outputPort;
	private final PolicyResolver<DomainAggregate> policyResolver;
	
	@Override
	public DomainAggregate create(DomainAggregate businessDomainAggregate) {
		DomainAggregate domainAggregate = DomainAggregate.create(businessDomainAggregate, policyResolver.resolve(CREATE, DomainAggregate.class));
		return outputPort.save(domainAggregate);
	}
	
	@Override
	public DomainAggregate retrieveById(String id) {
		return outputPort.findById(id);
	}
	
	@Override
	public List<DomainAggregate> retrieveAll() {
		return outputPort.findAll().stream().collect(Collectors.toList());
	}
	
	@Override
	public DomainAggregate update(DomainAggregate businessDomainAggregate) {
		DomainAggregate domainAggregate = businessDomainAggregate.update(businessDomainAggregate, policyResolver.resolve(UPDATE, DomainAggregate.class));
		return outputPort.update(domainAggregate);
	}
	
	@Override
	public void deleteById(String id) {
		outputPort.deleteById(id);
	}
}
