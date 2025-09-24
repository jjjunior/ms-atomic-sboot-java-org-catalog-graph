package br.com.jstack.org.catalog.graph.framework.adapter.mapper;

import br.com.jstack.org.catalog.graph.domain.aggregate.Tenant;
import br.com.jstack.org.catalog.graph.framework.adapter.output.node.TenantNode;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TenantMapper {
	
	// Domain -> Node
	TenantNode domainToNode(Tenant domain);
	
	// Node -> Domain
	Tenant nodeToDomain(TenantNode node);
	
	Tenant requestToDomain(Tenant tenant);
}