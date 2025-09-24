package br.com.jstack.org.catalog.graph.framework.adapter.input.rest;

import br.com.jstack.org.catalog.graph.api.DomainsApi;
import br.com.jstack.org.catalog.graph.application.usecase.CreateUseCase;
import br.com.jstack.org.catalog.graph.application.usecase.DeleteByIdUseCase;
import br.com.jstack.org.catalog.graph.application.usecase.RetrieveAllUseCase;
import br.com.jstack.org.catalog.graph.application.usecase.RetrieveByIdUseCase;
import br.com.jstack.org.catalog.graph.application.usecase.UpdateUseCase;
import br.com.jstack.org.catalog.graph.domain.aggregate.Domain;
import br.com.jstack.org.catalog.graph.framework.adapter.mapper.DomainMapper;
import br.com.jstack.org.catalog.graph.model.DomainRequest;
import br.com.jstack.org.catalog.graph.model.ItemResponse;
import br.com.jstack.org.catalog.graph.model.LinkCapabilitiesRequest;
import br.com.jstack.org.catalog.graph.model.LinkSubdomainsRequest;
import br.com.jstack.org.catalog.graph.model.PagedListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class DomainRestAdapter implements DomainsApi {
	
	private final DomainMapper                        mapper;
	private final CreateUseCase<Domain>               createUseCase;
	private final RetrieveByIdUseCase<Domain, String> retrieveByIdUseCase;
	private final RetrieveAllUseCase<Domain>          retrieveAllUseCase;
	private final UpdateUseCase<Domain>               updateUseCase;
	private final DeleteByIdUseCase<Domain, String>   deleteUseCase;
	
	@Override
	public ResponseEntity<ItemResponse> createDomain(DomainRequest domainRequest, String xNamespace) {
		return DomainsApi.super.createDomain(domainRequest, xNamespace);
	}
	
	@Override
	public ResponseEntity<Void> deleteDomain(String domainAcronym, String xNamespace) {
		return DomainsApi.super.deleteDomain(domainAcronym, xNamespace);
	}
	
	@Override
	public ResponseEntity<ItemResponse> getDomain(String domainAcronym, String xNamespace) {
		return DomainsApi.super.getDomain(domainAcronym, xNamespace);
	}
	
	@Override
	public ResponseEntity<Void> linkCapabilitiesToDomain(String domainAcronym, LinkCapabilitiesRequest linkCapabilitiesRequest, String xNamespace) {
		return DomainsApi.super.linkCapabilitiesToDomain(domainAcronym, linkCapabilitiesRequest, xNamespace);
	}
	
	@Override
	public ResponseEntity<Void> linkSubdomainsToDomain(String domainAcronym, LinkSubdomainsRequest linkSubdomainsRequest, String xNamespace) {
		return DomainsApi.super.linkSubdomainsToDomain(domainAcronym, linkSubdomainsRequest, xNamespace);
	}
	
	@Override
	public ResponseEntity<PagedListResponse> listDomainCapabilities(String domainAcronym, String xNamespace, Integer page, Integer size) {
		return DomainsApi.super.listDomainCapabilities(domainAcronym, xNamespace, page, size);
	}
	
	@Override
	public ResponseEntity<PagedListResponse> listDomains(String xNamespace, String q, Integer page, Integer size) {
		return DomainsApi.super.listDomains(xNamespace, q, page, size);
	}
	
	@Override
	public ResponseEntity<PagedListResponse> listSubdomainsOfDomain(String domainAcronym, String xNamespace, Integer page, Integer size) {
		return DomainsApi.super.listSubdomainsOfDomain(domainAcronym, xNamespace, page, size);
	}
	
	@Override
	public ResponseEntity<Void> unlinkCapabilityFromDomain(String domainAcronym, String capSlug, String xNamespace) {
		return DomainsApi.super.unlinkCapabilityFromDomain(domainAcronym, capSlug, xNamespace);
	}
	
	@Override
	public ResponseEntity<Void> unlinkSubdomainFromDomain(String domainAcronym, String subAcronym, String xNamespace) {
		return DomainsApi.super.unlinkSubdomainFromDomain(domainAcronym, subAcronym, xNamespace);
	}
	
	@Override
	public ResponseEntity<ItemResponse> updateDomain(String domainAcronym, DomainRequest domainRequest, String xNamespace) {
		return DomainsApi.super.updateDomain(domainAcronym, domainRequest, xNamespace);
	}


//	@Override
//	public ResponseEntity<Void> createBusinessDomain(DomainRequest businessDomainRequest) {
//		Domain businessDomain = mapper.toDomain(businessDomainRequest)
//		Domain                 created  = createUseCase.create(businessDomain);
//		return ResponseEntity.status(HttpStatus.CREATED).build();
//	}
//
//	@Override
//	public ResponseEntity<Void> deleteBusinessDomain(String canonicalId) {
//		deleteUseCase.deleteById(canonicalId);
//		return ResponseEntity.noContent().build();
//	}
//
//	@Override
//	public ResponseEntity<List<BusinessDomainResponse>> listBusinessDomains() {
//		List<Domain> businessDomains = retrieveAllUseCase.retrieveAll();
//		List<BusinessDomainResponse> responses = businessDomains.stream()
//			.map(mapper::toResponse)
//			.collect(Collectors.toList());
//
//		return ResponseEntity.ok(responses);
//	}
//
//	@Override
//	public ResponseEntity<BusinessDomainResponse> retrieveBusinessDomain(String canonicalId) {
//		Domain                 businessDomain = retrieveByIdUseCase.retrieveById(canonicalId);
//		BusinessDomainResponse response       = mapper.toResponse(businessDomain);
//
//		return ResponseEntity.ok(response);
//	}
//
//	@Override
//	public ResponseEntity<BusinessDomainResponse> updateBusinessDomain(String canonicalId, BusinessDomainRequest businessDomainRequest) {
//		Domain businessDomain = mapper.toDomain(businessDomainRequest);
//		businessDomain.setCanonicalId(canonicalId);
//
//		Domain                 updated  = updateUseCase.update(businessDomain);
//		BusinessDomainResponse response = mapper.toResponse(updated);
//
//		return ResponseEntity.ok(response);
//	}
}