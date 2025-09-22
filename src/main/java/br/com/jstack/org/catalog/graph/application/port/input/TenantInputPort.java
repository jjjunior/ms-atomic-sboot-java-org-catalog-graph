package br.com.jstack.org.catalog.graph.application.port.input;

import java.util.List;
import java.util.stream.Collectors;

import br.com.jstack.org.catalog.graph.application.port.output.TenantOutputPort;
import br.com.jstack.org.catalog.graph.application.usecase.CreateUseCase;
import br.com.jstack.org.catalog.graph.application.usecase.DeleteByIdUseCase;
import br.com.jstack.org.catalog.graph.application.usecase.RetrieveAllUseCase;
import br.com.jstack.org.catalog.graph.application.usecase.RetrieveByIdUseCase;
import br.com.jstack.org.catalog.graph.application.usecase.UpdateUseCase;
import br.com.jstack.org.catalog.graph.domain.aggregate.TenantAggregate;
import br.com.jstack.org.catalog.graph.domain.policy.PolicyResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static br.com.jstack.org.catalog.graph.domain.vo.OperationType.CREATE;
import static br.com.jstack.org.catalog.graph.domain.vo.OperationType.UPDATE;

@Component
@RequiredArgsConstructor
public class TenantInputPort implements
	CreateUseCase<TenantAggregate>,
	RetrieveByIdUseCase<TenantAggregate, String>,
	RetrieveAllUseCase<TenantAggregate>,
	UpdateUseCase<TenantAggregate>,
	DeleteByIdUseCase<TenantAggregate, String> {
	
	private final TenantOutputPort                outputPort;
	private final PolicyResolver<TenantAggregate> policyResolver;
	
	@Override
	public TenantAggregate create(TenantAggregate tenantAggregateCompany) {
		TenantAggregate tenantAggregate = TenantAggregate.create(tenantAggregateCompany, policyResolver.resolve(CREATE, TenantAggregate.class));
		return outputPort.save(tenantAggregate);
	}
	
	@Override
	public TenantAggregate retrieveById(String id) {
		return outputPort.findById(id);
	}
	
	@Override
	public List<TenantAggregate> retrieveAll() {
		return outputPort.findAll().stream().collect(Collectors.toList());
	}
	
	@Override
	public TenantAggregate update(TenantAggregate tenantAggregateCompany) {
		TenantAggregate tenantAggregate = tenantAggregateCompany.rename(tenantAggregateCompany.getName(), policyResolver.resolve(UPDATE, TenantAggregate.class));
		return outputPort.update(tenantAggregate);
	}
	
	@Override
	public void deleteById(String id) {
		outputPort.deleteById(id);
	}
}
