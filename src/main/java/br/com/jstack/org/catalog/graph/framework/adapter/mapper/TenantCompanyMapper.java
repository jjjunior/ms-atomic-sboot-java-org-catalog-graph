package br.com.jstack.org.catalog.graph.framework.adapter.mapper;

import br.com.jstack.org.catalog.graph.domain.model.TenantCompany;
import br.com.jstack.org.catalog.graph.domain.node.TenantCompanyNode;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TenantCompanyMapper {
	
	// Domain -> Node
	TenantCompanyNode toNode(TenantCompany domain);
	
	// Node -> Domain
	TenantCompany toDomain(TenantCompanyNode node);
}