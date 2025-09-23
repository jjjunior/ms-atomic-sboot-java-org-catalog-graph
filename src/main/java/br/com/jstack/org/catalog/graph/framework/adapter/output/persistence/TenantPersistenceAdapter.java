package br.com.jstack.org.catalog.graph.framework.adapter.output.persistence;

import java.util.List;
import java.util.Optional;

import br.com.jstack.org.catalog.graph.application.port.output.TenantOutputPort;
import br.com.jstack.org.catalog.graph.domain.aggregate.TenantAggregate;
import br.com.jstack.org.catalog.graph.framework.adapter.mapper.TenantMapper;
import br.com.jstack.org.catalog.graph.framework.adapter.output.node.TenantNode;
import br.com.jstack.org.catalog.graph.framework.adapter.output.repository.TenantRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TenantPersistenceAdapter implements TenantOutputPort {
	
	private final TenantRepository repository;
	private final TenantMapper     mapper;
	
	@Override
	public TenantAggregate save(TenantAggregate tenantAggregate) {
		TenantNode node  = mapper.domainToNode(tenantAggregate);
		TenantNode saved = repository.save(node);
		return mapper.nodeToDomain(saved);
	}
	
	@Override
	public TenantAggregate findById(String tenantId) {
		Optional<TenantNode> tenantCompanyNode = repository.findById(tenantId);
		return tenantCompanyNode.map(mapper::nodeToDomain).orElse(null);
	}
	
	@Override
	public List<TenantAggregate> findAll() {
		return repository.findAll().stream().map(mapper::nodeToDomain).toList();
	}
	
	@Transactional
	@Override
	public void deleteById(String tenantId) {
		repository.deleteById(tenantId);
	}
	
	@Override
	public TenantAggregate update(TenantAggregate tenantAggregate) {
		TenantNode node  = mapper.domainToNode(tenantAggregate);
		TenantNode saved = repository.save(node);
		return mapper.nodeToDomain(saved);
	}
	
	@Override
	public Boolean existsByTenantId(String tenantId) {
		return repository.existsByTenantId(tenantId);
	}
}