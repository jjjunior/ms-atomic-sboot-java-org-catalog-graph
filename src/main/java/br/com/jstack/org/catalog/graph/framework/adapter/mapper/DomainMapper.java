package br.com.jstack.org.catalog.graph.framework.adapter.mapper;

import br.com.jstack.org.catalog.graph.domain.aggregate.Domain;
import br.com.jstack.org.catalog.graph.framework.adapter.output.node.DomainNode;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DomainMapper {
	
	Domain toDomain(Domain domain);
	
	DomainNode toNode(Domain domain);
	
	Domain toDomain(DomainNode node);
}