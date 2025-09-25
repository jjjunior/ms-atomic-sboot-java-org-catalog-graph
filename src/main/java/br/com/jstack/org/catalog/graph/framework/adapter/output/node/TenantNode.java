package br.com.jstack.org.catalog.graph.framework.adapter.output.node;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import br.com.jstack.org.catalog.graph.domain.vo.Lifecycle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.neo4j.core.schema.CompositeProperty;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

/**
 * Node do Neo4j para persistência de Tenant.
 * Reflete os campos do TenantAggregate (incluindo base CatalogAggregate).
 */
@Data
@Node("Tenant")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = "tenantId")
public class TenantNode {
	
	@Id
	private String              tenantId;
	private String              kind;
	private String              description;
	private String              owner;
	private Lifecycle           lifecycle;
	@CreatedBy
	private String              createdBy;
	@CreatedDate
	private LocalDateTime       createdAt;
	@LastModifiedBy
	private String              updatedBy;
	@LastModifiedDate
	private LocalDateTime       updatedAt;
	
	@Builder.Default
	@CompositeProperty(prefix = "labels.")
	private Map<String,String> labels = new HashMap<>();
	
	@Builder.Default
	@CompositeProperty(prefix = "ann.")
	private Map<String,String> annotations = new HashMap<>();
	
	
	@Relationship(type = "TENANT_OWNS_DOMAIN", direction = Relationship.Direction.OUTGOING)
	private Set<DomainNode> domains;
}