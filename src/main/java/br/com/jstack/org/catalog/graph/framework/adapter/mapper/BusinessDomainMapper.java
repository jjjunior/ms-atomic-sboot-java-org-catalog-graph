package br.com.jstack.org.catalog.graph.framework.adapter.mapper;

import br.com.jstack.org.catalog.graph.domain.model.BusinessDomain;
import br.com.jstack.org.catalog.graph.model.BusinessDomainRequest;
import br.com.jstack.org.catalog.graph.model.BusinessDomainResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BusinessDomainMapper {
	
	BusinessDomain toDomain(BusinessDomainRequest request);
	
	@Mapping(target = "audit.updatedBy", source = "updatedBy")
	@Mapping(target = "audit.updatedAt", source = "updatedAt")
	@Mapping(target = "audit.createdBy", source = "createdBy")
	@Mapping(target = "audit.createdAt", source = "createdAt")
	BusinessDomainResponse toResponse(BusinessDomain domain);
	
}