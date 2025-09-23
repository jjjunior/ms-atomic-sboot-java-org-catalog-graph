package br.com.jstack.org.catalog.graph.framework.adapter.mapper;

import br.com.jstack.org.catalog.graph.domain.aggregate.TenantAggregate;
import br.com.jstack.org.catalog.graph.framework.adapter.output.node.TenantNode;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TenantMapper {
	
	// Domain -> Node
	TenantNode domainToNode(TenantAggregate domain);
	
	// Node -> Domain
	TenantAggregate nodeToDomain(TenantNode node);
	
	TenantAggregate requestToDomain(TenantAggregate tenantAggregate);
}