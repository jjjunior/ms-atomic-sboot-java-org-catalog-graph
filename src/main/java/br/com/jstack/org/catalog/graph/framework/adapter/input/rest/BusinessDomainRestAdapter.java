package br.com.jstack.org.catalog.graph.framework.adapter.input.rest;

import java.util.List;
import java.util.stream.Collectors;

import br.com.jstack.org.catalog.graph.api.BusinessDomainApi;
import br.com.jstack.org.catalog.graph.application.usecase.CreateUseCase;
import br.com.jstack.org.catalog.graph.application.usecase.DeleteByIdUseCase;
import br.com.jstack.org.catalog.graph.application.usecase.RetrieveAllUseCase;
import br.com.jstack.org.catalog.graph.application.usecase.RetrieveByIdUseCase;
import br.com.jstack.org.catalog.graph.application.usecase.UpdateUseCase;
import br.com.jstack.org.catalog.graph.domain.model.BusinessDomain;
import br.com.jstack.org.catalog.graph.framework.adapter.mapper.BusinessDomainMapper;
import br.com.jstack.org.catalog.graph.model.BusinessDomainRequest;
import br.com.jstack.org.catalog.graph.model.BusinessDomainResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class BusinessDomainRestAdapter implements BusinessDomainApi {
	
	private final BusinessDomainMapper                        mapper;
	private final CreateUseCase<BusinessDomain>               createUseCase;
	private final RetrieveByIdUseCase<BusinessDomain, String> retrieveByIdUseCase;
	private final RetrieveAllUseCase<BusinessDomain>          retrieveAllUseCase;
	private final UpdateUseCase<BusinessDomain>               updateUseCase;
	private final DeleteByIdUseCase<BusinessDomain, String>   deleteUseCase;
	
	@Override
	public ResponseEntity<BusinessDomainResponse> createBusinessDomain(BusinessDomainRequest businessDomainRequest) {
		BusinessDomain businessDomain = mapper.toDomain(businessDomainRequest);
		
		BusinessDomain         created  = createUseCase.create(businessDomain);
		BusinessDomainResponse response = mapper.toResponse(created);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@Override
	public ResponseEntity<Void> deleteBusinessDomain(String canonicalId) {
		deleteUseCase.deleteById(canonicalId);
		return ResponseEntity.noContent().build();
	}
	
	@Override
	public ResponseEntity<List<BusinessDomainResponse>> listBusinessDomains() {
		List<BusinessDomain> businessDomains = retrieveAllUseCase.retrieveAll();
		List<BusinessDomainResponse> responses = businessDomains.stream()
			.map(mapper::toResponse)
			.collect(Collectors.toList());

		return ResponseEntity.ok(responses);
	}
	
	@Override
	public ResponseEntity<BusinessDomainResponse> retrieveBusinessDomain(String canonicalId) {
		BusinessDomain businessDomain = retrieveByIdUseCase.retrieveById(canonicalId);
		BusinessDomainResponse response       = mapper.toResponse(businessDomain);
		
		return ResponseEntity.ok(response);
	}
	
	@Override
	public ResponseEntity<BusinessDomainResponse> updateBusinessDomain(String canonicalId, BusinessDomainRequest businessDomainRequest) {
		BusinessDomain businessDomain = mapper.toDomain(businessDomainRequest);
		businessDomain.withCanonicalId(canonicalId);
		
		BusinessDomain         updated  = updateUseCase.update(businessDomain);
		BusinessDomainResponse response = mapper.toResponse(updated);
		
		return ResponseEntity.ok(response);
	}
}