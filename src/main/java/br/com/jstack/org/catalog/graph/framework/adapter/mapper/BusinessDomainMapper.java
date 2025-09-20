package br.com.jstack.org.catalog.graph.framework.adapter.mapper;

import br.com.jstack.org.catalog.graph.domain.model.BusinessDomain;
import br.com.jstack.org.catalog.graph.domain.node.BusinessDomainNode;
import br.com.jstack.org.catalog.graph.model.BusinessDomainRequest;
import br.com.jstack.org.catalog.graph.model.BusinessDomainResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BusinessDomainMapper {
	@Mapping(target = "rejectedAt", ignore = true)
	@Mapping(target = "approvedAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "rejectedBy", ignore = true)
	@Mapping(target = "approvedBy", ignore = true)
	@Mapping(target = "updatedBy", ignore = true)
	@Mapping(target = "createdBy", ignore = true)
	@Mapping(target = "tenantCompanies", ignore = true)
	BusinessDomain toDomain(BusinessDomainRequest request);
	
	@Mapping(target = "audit.updatedBy", source = "updatedBy")
	@Mapping(target = "audit.updatedAt", source = "updatedAt")
	@Mapping(target = "audit.createdBy", source = "createdBy")
	@Mapping(target = "audit.createdAt", source = "createdAt")
	BusinessDomainResponse toResponse(BusinessDomain domain);
	BusinessDomainNode toNode(BusinessDomain domain);
	
	BusinessDomain toDomain(BusinessDomainNode node);
	
	
	
}