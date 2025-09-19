package br.com.jstack.org.catalog.graph.framework.adapter.output.repository;

import br.com.jstack.org.catalog.graph.domain.node.TenantCompanyNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TenantCompanyRepository extends Neo4jRepository<TenantCompanyNode, String> {
	boolean existsByTenantId(String tenantId);
	
}