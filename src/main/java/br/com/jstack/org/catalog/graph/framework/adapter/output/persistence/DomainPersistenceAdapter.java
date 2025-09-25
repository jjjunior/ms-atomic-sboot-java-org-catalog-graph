package br.com.jstack.org.catalog.graph.framework.adapter.output.persistence;

import java.util.List;

import br.com.jstack.org.catalog.graph.application.port.output.DomainOutputPort;
import br.com.jstack.org.catalog.graph.domain.aggregate.Domain;
import br.com.jstack.org.catalog.graph.framework.adapter.mapper.DomainMapper;
import br.com.jstack.org.catalog.graph.framework.adapter.output.node.DomainNode;
import br.com.jstack.org.catalog.graph.framework.adapter.output.repository.DomainRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DomainPersistenceAdapter implements DomainOutputPort {
	
	private final DomainRepository repository;
	private final DomainMapper     mapper;
	
	@Override
	public boolean existsByTenantAndAcronym(String tenantId, String acronym) {
		return repository.existsByTenantAndAcronym(tenantId, acronym);
	}
	
	@Transactional
	@Override
	public Domain insert(Domain domain) {
		DomainNode node  = mapper.toNode(domain);
		DomainNode saved = repository.save(node);
		return mapper.toDomain(saved);
	}
	
	@Override
	public Domain findById(String id) {
		DomainNode node = repository.findById(id).isPresent() ? repository.findById(id).get() : null;
		if (node != null) {
			return mapper.toDomain(node);
		}
		return null;
	}
	
	@Override
	public List<Domain> findAll() {
		return repository.findAll().stream().map(mapper::toDomain).toList();
	}
	
	@Transactional
	@Override
	public Domain update(Domain patch) {
		DomainNode node  = mapper.toNode(patch);
		DomainNode saved = repository.save(node);
		return mapper.toDomain(saved);
	}
	
	@Transactional
	@Override
	public void deleteById(String id) {
		repository.deleteById(id);
	}
	
}