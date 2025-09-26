package br.com.jstack.org.catalog.graph.framework.adapter.mapper;

import br.com.jstack.org.catalog.graph.domain.aggregate.Tenant;
import br.com.jstack.org.catalog.graph.framework.adapter.output.node.TenantNode;
import br.com.jstack.org.catalog.graph.model.ItemResponse;
import br.com.jstack.org.catalog.graph.model.TenantRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TenantMapper {
	
	
	// Request -> Domain
	@Mapping(target = "createdBy", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedBy", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "domains", ignore = true)
	@Mapping(target = "owner", source = "spec.owner")
	@Mapping(target = "name", source = "metadata.name")
	@Mapping(target = "lifecycle", source = "status.lifecycle")
	@Mapping(target = "labels", source = "metadata.labels")
	@Mapping(target = "description", source = "metadata.description")
	@Mapping(target = "annotations", source = "metadata.annotations")
	Tenant requestToDomain(TenantRequest request);
	
	// Domain -> Node
	@Mapping(target = "tenantId", source = "name")
	TenantNode domainToNode(Tenant domain);
	
	//	 Node -> Domain
	@Mapping(target = "name", source = "tenantId")
	Tenant nodeToDomain(TenantNode node);
	
	@Mapping(target = "timestamp", ignore = true)
	@Mapping(target = "requestId", ignore = true)
	@Mapping(target = "path", ignore = true)
	@Mapping(target = "messageStatus", ignore = true)
	@Mapping(target = "data", ignore = true)
	@Mapping(target = "codeStatus", ignore = true)
	ItemResponse domainToResponse(Tenant created);
}