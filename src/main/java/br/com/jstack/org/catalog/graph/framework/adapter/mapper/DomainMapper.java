package br.com.jstack.org.catalog.graph.framework.adapter.mapper;

import br.com.jstack.org.catalog.graph.domain.aggregate.DomainAggregate;
import br.com.jstack.org.catalog.graph.framework.adapter.output.node.DomainNode;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DomainMapper {
	
	DomainAggregate toDomain(DomainAggregate domainAggregate);
	
	DomainNode toNode(DomainAggregate domainAggregate);
	
	DomainAggregate toDomain(DomainNode node);
}