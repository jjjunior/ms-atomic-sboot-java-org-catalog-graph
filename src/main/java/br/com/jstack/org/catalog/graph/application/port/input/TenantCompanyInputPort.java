package br.com.jstack.org.catalog.graph.application.port.input;

import java.util.List;
import java.util.stream.Collectors;

import br.com.jstack.org.catalog.graph.application.port.output.TenantCompanyOutputPort;
import br.com.jstack.org.catalog.graph.application.usecase.CreateUseCase;
import br.com.jstack.org.catalog.graph.application.usecase.DeleteByIdUseCase;
import br.com.jstack.org.catalog.graph.application.usecase.RetrieveAllUseCase;
import br.com.jstack.org.catalog.graph.application.usecase.RetrieveByIdUseCase;
import br.com.jstack.org.catalog.graph.application.usecase.UpdateUseCase;
import br.com.jstack.org.catalog.graph.domain.model.TenantCompany;
import br.com.jstack.org.catalog.graph.domain.policy.PolicyResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static br.com.jstack.org.catalog.graph.domain.vo.OperationType.CREATE;
import static br.com.jstack.org.catalog.graph.domain.vo.OperationType.UPDATE;

@Component
@RequiredArgsConstructor
public class TenantCompanyInputPort implements
	CreateUseCase<TenantCompany>,
	RetrieveByIdUseCase<TenantCompany, String>,
	RetrieveAllUseCase<TenantCompany>,
	UpdateUseCase<TenantCompany>,
	DeleteByIdUseCase<TenantCompany, String> {
	
	private final TenantCompanyOutputPort       outputPort;
	private final PolicyResolver<TenantCompany> policyResolver;
	
	@Override
	public TenantCompany create(TenantCompany tenantCompany) {
		TenantCompany tenant = TenantCompany.create(tenantCompany,policyResolver.resolve(CREATE, TenantCompany.class));
		return outputPort.save(tenant);
	}
	
	@Override
	public TenantCompany retrieveById(String id) {
		return outputPort.findById(id);
	}
	
	@Override
	public List<TenantCompany> retrieveAll() {
		return outputPort.findAll().stream().collect(Collectors.toList());
	}
	
	@Override
	public TenantCompany update(TenantCompany tenantCompany) {
		TenantCompany tenant = tenantCompany.updateName(tenantCompany.name(), policyResolver.resolve(UPDATE, TenantCompany.class));
		return outputPort.update(tenant);
	}
	
	@Override
	public void deleteById(String id) {
		outputPort.deleteById(id);
	}
}
