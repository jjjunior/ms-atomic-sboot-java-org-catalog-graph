package br.com.jstack.org.catalog.graph.framework.adapter.mapper;

import br.com.jstack.org.catalog.graph.domain.model.TenantCompany;
import br.com.jstack.org.catalog.graph.domain.node.TenantCompanyNode;
import br.com.jstack.org.catalog.graph.domain.vo.TenantStatus;
import br.com.jstack.org.catalog.graph.model.TenantCompanyRequest;
import br.com.jstack.org.catalog.graph.model.TenantCompanyResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ValueMapping;
import org.mapstruct.ValueMappings;

@Mapper(componentModel = "spring")
public interface TenantCompanyMapper {
	
	// Domain -> Node
	TenantCompanyNode domainToNode(TenantCompany domain);
	
	// Node -> Domain
	TenantCompany nodeToDomain(TenantCompanyNode node);
	
	@Mapping(target = "audit.updatedBy", source = "updatedBy")
	@Mapping(target = "audit.updatedAt", source = "updatedAt")
	@Mapping(target = "audit.createdBy", source = "createdBy")
	@Mapping(target = "audit.createdAt", source = "createdAt")
	TenantCompanyResponse domainToResponse(TenantCompany domain);
	
	@Mapping(target = "updatedBy", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "createdBy", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "businessDomains", ignore = true)
	TenantCompany requestToDomain(TenantCompanyRequest request);
	
	@ValueMappings({
		@ValueMapping(source = "DEPRECATED", target = MappingConstants.NULL)
	})
	TenantStatus toTenantStatus(TenantCompanyRequest.StatusEnum status);
	
	TenantCompanyRequest.StatusEnum toStatusEnum(TenantStatus status);
}