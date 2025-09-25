package br.com.jstack.org.catalog.graph.application.service;

import java.util.ArrayList;
import java.util.List;

import br.com.jstack.org.catalog.graph.application.port.input.DomainUseCase;
import br.com.jstack.org.catalog.graph.application.port.output.DomainOutputPort;
import br.com.jstack.org.catalog.graph.domain.aggregate.Domain;
import br.com.jstack.org.catalog.graph.domain.policy.shared.PolicyResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static br.com.jstack.org.catalog.graph.domain.vo.OperationType.CREATE;
import static br.com.jstack.org.catalog.graph.domain.vo.OperationType.UPDATE;

@Service
@RequiredArgsConstructor
public class DomainService implements DomainUseCase {
	
	private final DomainOutputPort outputPort;
	private final PolicyResolver   policy;
	
	@Override
	public Domain create(Domain domain) {
		policy.validate(CREATE, domain, Domain.class);
		return outputPort.insert(domain);
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
	public Domain update(Domain domain) {
		policy.validate(UPDATE, domain, Domain.class);
		return outputPort.update(domain);
	}
	
	@Override
	public void deleteById(String id) {
		outputPort.deleteById(id);
	}
}
