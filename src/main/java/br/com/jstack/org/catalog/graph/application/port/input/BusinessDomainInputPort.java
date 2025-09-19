package br.com.jstack.org.catalog.graph.application.port.input;

import java.util.List;
import java.util.stream.Collectors;

import br.com.jstack.org.catalog.graph.application.port.output.BusinessDomainOutputPort;
import br.com.jstack.org.catalog.graph.application.usecase.CreateUseCase;
import br.com.jstack.org.catalog.graph.application.usecase.DeleteByIdUseCase;
import br.com.jstack.org.catalog.graph.application.usecase.RetrieveAllUseCase;
import br.com.jstack.org.catalog.graph.application.usecase.RetrieveByIdUseCase;
import br.com.jstack.org.catalog.graph.application.usecase.UpdateUseCase;
import br.com.jstack.org.catalog.graph.domain.model.BusinessDomain;
import br.com.jstack.org.catalog.graph.domain.policy.PolicyResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static br.com.jstack.org.catalog.graph.domain.vo.OperationType.CREATE;
import static br.com.jstack.org.catalog.graph.domain.vo.OperationType.UPDATE;

@Component
@RequiredArgsConstructor
public class BusinessDomainInputPort implements
	CreateUseCase<BusinessDomain>,
	RetrieveByIdUseCase<BusinessDomain, String>,
	RetrieveAllUseCase<BusinessDomain>,
	UpdateUseCase<BusinessDomain>,
	DeleteByIdUseCase<BusinessDomain, String> {
	
	private final BusinessDomainOutputPort       outputPort;
	private final PolicyResolver<BusinessDomain> policyResolver;
	
	@Override
	public BusinessDomain create(BusinessDomain businessDomain) {
		BusinessDomain domain = BusinessDomain.create(businessDomain,policyResolver.resolve(CREATE, BusinessDomain.class));
		return outputPort.save(domain);
	}
	
	@Override
	public BusinessDomain retrieveById(String id) {
		return outputPort.findById(id);
	}
	
	@Override
	public List<BusinessDomain> retrieveAll() {
		return outputPort.findAll().stream().collect(Collectors.toList());
	}
	
	@Override
	public BusinessDomain update(BusinessDomain businessDomain) {
		BusinessDomain domain = businessDomain.update(businessDomain,policyResolver.resolve(UPDATE,BusinessDomain.class));
		return outputPort.update(domain);
	}
	
	@Override
	public void deleteById(String id) {
		outputPort.deleteById(id);
	}
}
