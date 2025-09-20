package br.com.jstack.org.catalog.graph.framework.adapter.input.rest;

import java.util.List;

import br.com.jstack.org.catalog.graph.api.TenantCompanyApi;
import br.com.jstack.org.catalog.graph.application.usecase.CreateUseCase;
import br.com.jstack.org.catalog.graph.application.usecase.DeleteByIdUseCase;
import br.com.jstack.org.catalog.graph.application.usecase.RetrieveAllUseCase;
import br.com.jstack.org.catalog.graph.application.usecase.RetrieveByIdUseCase;
import br.com.jstack.org.catalog.graph.application.usecase.UpdateUseCase;
import br.com.jstack.org.catalog.graph.domain.model.TenantCompany;
import br.com.jstack.org.catalog.graph.framework.adapter.mapper.TenantCompanyMapper;
import br.com.jstack.org.catalog.graph.model.TenantCompanyRequest;
import br.com.jstack.org.catalog.graph.model.TenantCompanyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CompanyTenantRestAdapter implements TenantCompanyApi {
	
	private final TenantCompanyMapper          mapper;
	private final CreateUseCase<TenantCompany> createUseCase;
	private final RetrieveByIdUseCase<TenantCompany, String> retrieveByIdUseCase;
	private final RetrieveAllUseCase<TenantCompany>          retrieveAllUseCase;
	private final UpdateUseCase<TenantCompany>               updateUseCase;
	private final DeleteByIdUseCase<TenantCompany, String>   deleteUseCase;
	
	
	@Override
	public ResponseEntity<TenantCompanyResponse> createTenant(TenantCompanyRequest tenantCompanyRequest) {
		TenantCompany tenantCompany = mapper.requestToDomain(tenantCompanyRequest);
		
		TenantCompany         created  = createUseCase.create(tenantCompany);
		TenantCompanyResponse response = mapper.domainToResponse(created);

		return ResponseEntity.status(201).body(response);
	}
	
	@Override
	public ResponseEntity<Void> deleteTenant(String tenantId) {
		deleteUseCase.deleteById(tenantId);
		return ResponseEntity.noContent().build();
	}
	
	@Override
	public ResponseEntity<List<TenantCompanyResponse>> listTenants() {
		List<TenantCompany> businessDomains = retrieveAllUseCase.retrieveAll();
		List<TenantCompanyResponse> responses = businessDomains.stream()
			.map(mapper::domainToResponse)
			.toList();

		return ResponseEntity.ok(responses);
	}
	
	@Override
	public ResponseEntity<TenantCompanyResponse> retrieveTenant(String tenantId) {
		TenantCompany tenantCompany = retrieveByIdUseCase.retrieveById(tenantId);
		TenantCompanyResponse response = mapper.domainToResponse(tenantCompany);
		return ResponseEntity.ok(response);
	}
	
	@Override
	public ResponseEntity<TenantCompanyResponse> updateTenant(String tenantId, TenantCompanyRequest tenantCompanyRequest) {
		TenantCompany tenantCompany = mapper.requestToDomain(tenantCompanyRequest);
		tenantCompany.withTenantId(tenantId);
		
		TenantCompany         updated  = updateUseCase.update(tenantCompany);
		TenantCompanyResponse response = mapper.domainToResponse(updated);
		
		return ResponseEntity.ok(response);
	}
}