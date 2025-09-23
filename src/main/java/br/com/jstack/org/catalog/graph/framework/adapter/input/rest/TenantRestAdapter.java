package br.com.jstack.org.catalog.graph.framework.adapter.input.rest;

import br.com.jstack.org.catalog.graph.api.TenantsApi;
import br.com.jstack.org.catalog.graph.application.usecase.CreateUseCase;
import br.com.jstack.org.catalog.graph.application.usecase.DeleteByIdUseCase;
import br.com.jstack.org.catalog.graph.application.usecase.RetrieveAllUseCase;
import br.com.jstack.org.catalog.graph.application.usecase.RetrieveByIdUseCase;
import br.com.jstack.org.catalog.graph.application.usecase.UpdateUseCase;
import br.com.jstack.org.catalog.graph.domain.aggregate.TenantAggregate;
import br.com.jstack.org.catalog.graph.framework.adapter.mapper.TenantMapper;
import br.com.jstack.org.catalog.graph.model.ItemResponse;
import br.com.jstack.org.catalog.graph.model.PagedListResponse;
import br.com.jstack.org.catalog.graph.model.TenantRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TenantRestAdapter implements TenantsApi {
	
	private final TenantMapper                                 mapper;
	private final CreateUseCase<TenantAggregate>               createUseCase;
	private final RetrieveByIdUseCase<TenantAggregate, String> retrieveByIdUseCase;
	private final RetrieveAllUseCase<TenantAggregate>          retrieveAllUseCase;
	private final UpdateUseCase<TenantAggregate>               updateUseCase;
	private final DeleteByIdUseCase<TenantAggregate, String>   deleteUseCase;
	
	@Override
	public ResponseEntity<ItemResponse> createTenant(TenantRequest tenantRequest) {
		return TenantsApi.super.createTenant(tenantRequest);
	}
	
	@Override
	public ResponseEntity<Void> deleteTenant(String tenantId) {
		return TenantsApi.super.deleteTenant(tenantId);
	}
	
	@Override
	public ResponseEntity<ItemResponse> getTenant(String tenantId) {
		return TenantsApi.super.getTenant(tenantId);
	}
	
	@Override
	public ResponseEntity<PagedListResponse> listTenants(String q, Integer page, Integer size) {
		return TenantsApi.super.listTenants(q, page, size);
	}
	
	@Override
	public ResponseEntity<ItemResponse> updateTenant(String tenantId, TenantRequest tenantRequest) {
		return TenantsApi.super.updateTenant(tenantId, tenantRequest);
	}


//	@Override
//	public ResponseEntity<TenantCompanyResponse> createTenant(TenantCompanyRequest tenantCompanyRequest) {
//		Tenant tenant = mapper.requestToDomain(tenantCompanyRequest);
//
//		Tenant                created  = createUseCase.create(tenant);
//		TenantCompanyResponse response = mapper.domainToResponse(created);
//
//		return ResponseEntity.status(201).body(response);
//	}
//
//	@Override
//	public ResponseEntity<Void> deleteTenant(String tenantId) {
//		deleteUseCase.deleteById(tenantId);
//		return ResponseEntity.noContent().build();
//	}
//
//	@Override
//	public ResponseEntity<List<TenantCompanyResponse>> listTenants() {
//		List<Tenant> businessDomains = retrieveAllUseCase.retrieveAll();
//		List<TenantCompanyResponse> responses = businessDomains.stream()
//			.map(mapper::domainToResponse)
//			.toList();
//
//		return ResponseEntity.ok(responses);
//	}
//
//	@Override
//	public ResponseEntity<TenantCompanyResponse> retrieveTenant(String tenantId) {
//		Tenant                tenant   = retrieveByIdUseCase.retrieveById(tenantId);
//		TenantCompanyResponse response = mapper.domainToResponse(tenant);
//		return ResponseEntity.ok(response);
//	}
//
//	@Override
//	public ResponseEntity<TenantCompanyResponse> updateTenant(String tenantId, TenantCompanyRequest tenantCompanyRequest) {
//		Tenant tenant = mapper.requestToDomain(tenantCompanyRequest);
//		tenant.setTenantId(tenantId);
//
//		Tenant                updated  = updateUseCase.update(tenant);
//		TenantCompanyResponse response = mapper.domainToResponse(updated);
//
//		return ResponseEntity.ok(response);
//	}
}