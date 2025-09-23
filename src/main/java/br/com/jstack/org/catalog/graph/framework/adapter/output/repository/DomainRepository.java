package br.com.jstack.org.catalog.graph.framework.adapter.output.repository;

import br.com.jstack.org.catalog.graph.framework.adapter.output.node.DomainNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface DomainRepository extends Neo4jRepository<DomainNode, String> {
	
	@Query("""
		MATCH (d:BusinessDomain{ tenantId:$tenantId, acronym:$acronym})
		RETURN count(d) > 0 AS exists
		""")
	boolean existsByTenantAndAcronym(String tenantId, String acronym);
	
}