package br.com.jstack.org.catalog.graph.application.service;

import java.util.ArrayList;
import java.util.List;

import br.com.jstack.org.catalog.graph.application.port.input.TenantUseCase;
import br.com.jstack.org.catalog.graph.application.port.output.TenantOutputPort;
import br.com.jstack.org.catalog.graph.domain.aggregate.Tenant;
import br.com.jstack.org.catalog.graph.domain.policy.shared.PolicyResolver;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static br.com.jstack.org.catalog.graph.domain.vo.OperationType.CREATE;
import static br.com.jstack.org.catalog.graph.domain.vo.OperationType.UPDATE;

@Service
@RequiredArgsConstructor
public class TenantService implements TenantUseCase {
	
	private final TenantOutputPort outputPort;
	private final PolicyResolver   policy;
	
	@Override
	public Tenant create(Tenant tenant) {
		policy.validate(CREATE,tenant ,Tenant.class);
		return outputPort.insert(tenant);
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
	public Tenant update(Tenant tenant) {
		policy.validate(UPDATE,tenant ,Tenant.class);
		return outputPort.update(tenant);
	}
	
	@Override
	public void deleteById(String id) {
		outputPort.deleteById(id);
	}
}
