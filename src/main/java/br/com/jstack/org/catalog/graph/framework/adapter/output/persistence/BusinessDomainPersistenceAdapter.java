package br.com.jstack.org.catalog.graph.framework.adapter.output.persistence;

import java.util.List;
import java.util.Optional;

import br.com.jstack.org.catalog.graph.application.port.output.BusinessDomainOutputPort;
import br.com.jstack.org.catalog.graph.domain.model.BusinessDomain;
import br.com.jstack.org.catalog.graph.framework.adapter.output.repository.BusinessDomainRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BusinessDomainPersistenceAdapter implements BusinessDomainOutputPort {
	
	private final BusinessDomainRepository businessDomainRepository;
	
	@Override
	public boolean existsByTenantAndAcronym(String tenantId, String acronym) {
		return businessDomainRepository.existsByTenantAndAcronym(tenantId, acronym);
	}
	
	@Transactional
	@Override
	public BusinessDomain save(BusinessDomain businessDomain) {
	
//		return businessDomainRepository.save(businessDomain);
		return null;
	}
	
	@Override
	public Optional<BusinessDomain> findById(String id) {
//		return businessDomainRepository.findById(id);
		return Optional.empty();
	}
	
	@Override
	public List<BusinessDomain> findAll() {
//		return businessDomainRepository.findAll();
		return List.of();
	}
	
	@Transactional
	@Override
	public void deleteById(String id) {
		businessDomainRepository.deleteById(id);
	}
	
	@Transactional
	@Override
	public BusinessDomain update(BusinessDomain patch) {
//		BusinessDomain current = businessDomainRepository.findById(patch.getId())
//			.orElseThrow(() -> new NoSuchElementException("Business Domain with id " + patch.getId() + " not found"));
//
//		if (patch.getName() != null) current.setName(patch.getName());
//		if (patch.getDescription() != null) current.setDescription(patch.getDescription());
//		if (patch.getActive() != null) current.setActive(patch.getActive());
//
//		return businessDomainRepository.save(current);
		return null;
	}
	

}