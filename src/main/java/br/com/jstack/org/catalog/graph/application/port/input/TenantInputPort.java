package br.com.jstack.org.catalog.graph.application.port.input;

import java.util.ArrayList;
import java.util.List;

import br.com.jstack.org.catalog.graph.application.port.output.TenantOutputPort;
import br.com.jstack.org.catalog.graph.application.usecase.CreateUseCase;
import br.com.jstack.org.catalog.graph.application.usecase.DeleteByIdUseCase;
import br.com.jstack.org.catalog.graph.application.usecase.RetrieveAllUseCase;
import br.com.jstack.org.catalog.graph.application.usecase.RetrieveByIdUseCase;
import br.com.jstack.org.catalog.graph.application.usecase.UpdateUseCase;
import br.com.jstack.org.catalog.graph.domain.aggregate.Tenant;
import br.com.jstack.org.catalog.graph.domain.policy.PolicyResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static br.com.jstack.org.catalog.graph.domain.vo.OperationType.CREATE;
import static br.com.jstack.org.catalog.graph.domain.vo.OperationType.UPDATE;

@Component
@RequiredArgsConstructor
public class TenantInputPort implements
	CreateUseCase<Tenant>,
	RetrieveByIdUseCase<Tenant, String>,
	RetrieveAllUseCase<Tenant>,
	UpdateUseCase<Tenant>,
	DeleteByIdUseCase<Tenant, String> {
	
	private final TenantOutputPort       outputPort;
	private final PolicyResolver<Tenant> policyResolver;
	
	@Override
	public Tenant create(Tenant tenantCompany) {
		Tenant tenant = Tenant.create(tenantCompany, policyResolver.resolve(CREATE, Tenant.class));
		return outputPort.save(tenant);
	}
	
	@Override
	public Tenant retrieveById(String id) {
		return outputPort.findById(id);
	}
	
	@Override
	public List<Tenant> retrieveAll() {
		return new ArrayList<>(outputPort.findAll());
	}
	
	@Override
	public Tenant update(Tenant tenantCompany) {
		Tenant tenant = tenantCompany.rename(tenantCompany.getName(), policyResolver.resolve(UPDATE, Tenant.class));
		return outputPort.update(tenant);
	}
	
	@Override
	public void deleteById(String id) {
		outputPort.deleteById(id);
	}
}
