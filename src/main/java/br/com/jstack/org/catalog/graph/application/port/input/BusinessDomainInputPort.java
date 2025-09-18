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
import br.com.jstack.org.catalog.graph.domain.policy.ValidationPolicy;
import br.com.jstack.org.catalog.graph.domain.vo.OperationType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
	public BusinessDomain create(@Valid BusinessDomain businessDomain) {
		ValidationPolicy<BusinessDomain> policy = policyResolver.resolve(OperationType.CREATE, BusinessDomain.class);
		policy.validate(businessDomain, OperationType.CREATE);
		return outputPort.save(businessDomain);
	}
	
	@Override
	public BusinessDomain retrieveById(String id) {
		return outputPort.findById(id).get();
	}
	
	@Override
	public List<BusinessDomain> retrieveAll() {
		return outputPort.findAll().stream().collect(Collectors.toList());
	}
	
	@Override
	public BusinessDomain update(@Valid BusinessDomain businessDomain) {
		ValidationPolicy<BusinessDomain> policy = policyResolver.resolve(OperationType.UPDATE, BusinessDomain.class);
		policy.validate(businessDomain, OperationType.UPDATE);
		return outputPort.save(businessDomain);
	}
	
	@Override
	public void deleteById(String id) {
		outputPort.deleteById(id);
	}
}
