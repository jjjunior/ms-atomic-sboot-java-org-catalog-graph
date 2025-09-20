package br.com.jstack.org.catalog.graph.framework.adapter.output.persistence;

import java.util.List;

import br.com.jstack.org.catalog.graph.application.port.output.BusinessDomainOutputPort;
import br.com.jstack.org.catalog.graph.domain.model.BusinessDomain;
import br.com.jstack.org.catalog.graph.domain.node.BusinessDomainNode;
import br.com.jstack.org.catalog.graph.framework.adapter.mapper.BusinessDomainMapper;
import br.com.jstack.org.catalog.graph.framework.adapter.output.repository.BusinessDomainRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BusinessDomainPersistenceAdapter implements BusinessDomainOutputPort {
	
	private final BusinessDomainRepository repository;
	private final BusinessDomainMapper     mapper;
	
	@Override
	public boolean existsByTenantAndAcronym(String tenantId, String acronym) {
		return repository.existsByTenantAndAcronym(tenantId, acronym);
	}
	
	@Transactional
	@Override
	public BusinessDomain save(BusinessDomain businessDomain) {
		BusinessDomainNode node = mapper.toNode(businessDomain);
		BusinessDomainNode saved = repository.save(node);
		return mapper.toDomain(saved);
	}
	
	@Override
	public BusinessDomain findById(String id) {
		BusinessDomainNode node = repository.findById(id).isPresent() ? repository.findById(id).get() : null;
		if(node != null) {
			return mapper.toDomain(node);
		}
		return null;
	}
	
	@Override
	public List<BusinessDomain> findAll() {
		return repository.findAll().stream().map(mapper::toDomain).toList();
	}
	
	@Transactional
	@Override
	public void deleteById(String id) {
		repository.deleteById(id);
	}
	
	@Transactional
	@Override
	public BusinessDomain update(BusinessDomain patch) {
		BusinessDomainNode node  = mapper.toNode(patch);
		BusinessDomainNode saved = repository.save(node);
		return mapper.toDomain(saved);
	}
}