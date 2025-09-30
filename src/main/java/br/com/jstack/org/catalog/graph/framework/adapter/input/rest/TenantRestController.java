package br.com.jstack.org.catalog.graph.framework.adapter.input.rest;

import java.util.List;

import br.com.jstack.org.catalog.graph.api.TenantsApi;
import br.com.jstack.org.catalog.graph.application.port.input.shared.CreateUseCase;
import br.com.jstack.org.catalog.graph.application.port.input.shared.DeleteByIdUseCase;
import br.com.jstack.org.catalog.graph.application.port.input.shared.RetrieveAllUseCase;
import br.com.jstack.org.catalog.graph.application.port.input.shared.RetrieveByIdUseCase;
import br.com.jstack.org.catalog.graph.application.port.input.shared.UpdateUseCase;
import br.com.jstack.org.catalog.graph.domain.aggregate.Tenant;
import br.com.jstack.org.catalog.graph.framework.adapter.mapper.TenantMapper;
import br.com.jstack.org.catalog.graph.model.ItemResponse;
import br.com.jstack.org.catalog.graph.model.PagedListResponse;
import br.com.jstack.org.catalog.graph.model.TenantRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TenantRestController implements TenantsApi {
	
	private final TenantMapper                        mapper;
	private final CreateUseCase<Tenant>               createUseCase;
	private final RetrieveByIdUseCase<Tenant, String> retrieveByIdUseCase;
	private final RetrieveAllUseCase<Tenant>          retrieveAllUseCase;
	private final UpdateUseCase<Tenant>               updateUseCase;
	private final DeleteByIdUseCase<Tenant, String>   deleteUseCase;
	
	@Override
	public ResponseEntity<ItemResponse> createTenant(TenantRequest tenantRequest) {
		Tenant       tenant   = mapper.requestToDomain(tenantRequest);
		Tenant       created  = createUseCase.create(tenant);
		ItemResponse response = mapper.domainToResponse(created);
		return ResponseEntity.status(201).body(response);
	}
	
	@Override
	public ResponseEntity<Void> deleteTenant(String tenantId) {
		deleteUseCase.deleteById(tenantId);
		return ResponseEntity.noContent().build();
	}
	
	@Override
	public ResponseEntity<ItemResponse> getTenant(String tenantId) {
		Tenant                tenant   = retrieveByIdUseCase.retrieveById(tenantId);
		return ResponseEntity.ok(new ItemResponse());
	}
	
	@Override
	public ResponseEntity<PagedListResponse> listTenants(String q, Integer page, Integer size) {
		List<Tenant> tenants = retrieveAllUseCase.retrieveAll();
//		List<Tenant> responses = businessDomains.stream()
//			.map(mapper::domainToResponse)
//			.toList();
		PagedListResponse response = new PagedListResponse();
		return ResponseEntity.ok(response);
	}
	
	@Override
	public ResponseEntity<ItemResponse> updateTenant(String tenantId, TenantRequest tenantRequest) {
		Tenant       tenant   = mapper.requestToDomain(tenantRequest);
//		tenant.setTenantId(tenantId);
		Tenant                updated  = updateUseCase.update(tenant);
		ItemResponse response = mapper.domainToResponse(updated);
		return ResponseEntity.ok(response);
	}
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
