package br.com.jstack.org.catalog.graph.framework.adapter.output.persistence;

import java.util.List;
import java.util.Optional;

import br.com.jstack.org.catalog.graph.application.port.output.TenantCompanyOutputPort;
import br.com.jstack.org.catalog.graph.domain.model.TenantCompany;
import br.com.jstack.org.catalog.graph.domain.node.TenantCompanyNode;
import br.com.jstack.org.catalog.graph.framework.adapter.mapper.TenantCompanyMapper;
import br.com.jstack.org.catalog.graph.framework.adapter.output.repository.TenantCompanyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TenantCompanyPersistenceAdapter implements TenantCompanyOutputPort {
	
	private final TenantCompanyRepository repository;
	private final TenantCompanyMapper mapper;
	
	@Override
	public TenantCompany save(TenantCompany node) {
		return null;
	}
	
	@Override
	public TenantCompany findById(String tenantId) {
		Optional<TenantCompanyNode> tenantCompanyNode = repository.findById(tenantId);
		if(tenantCompanyNode.isPresent()) {
			return mapper.toDomain(tenantCompanyNode.get());
		}
		return null;
	}
	
	@Override
	public List<TenantCompany> findAll() {
		return List.of();
	}
	
	@Transactional
	@Override
	public void deleteById(String tenantId) {
		repository.deleteById(tenantId);
	}
	
	@Override
	public TenantCompany update(TenantCompany node) {
		return null;
	}
	
	@Override
	public Boolean existsByTenantId(String tenantId) {
		return repository.existsByTenantId(tenantId);
	}
}