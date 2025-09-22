package br.com.jstack.org.catalog.graph.framework.adapter.output.repository;

import br.com.jstack.org.catalog.graph.framework.adapter.output.node.TenantNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TenantRepository extends Neo4jRepository<TenantNode, String> {
	boolean existsByTenantId(String tenantId);
	
}