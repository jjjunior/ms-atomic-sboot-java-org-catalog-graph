package br.com.jstack.org.catalog.graph.domain.node;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import br.com.jstack.org.catalog.graph.domain.vo.TenantStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Data
@Node("TenantCompany")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = "tenantId")
public class TenantCompanyNode {
	
	@Id
	private String tenantId;
	
	private String name;
	
	private TenantStatus status;
	
	@CreatedDate
	private LocalDateTime createdAt;
	
	@CreatedBy
	private String createdBy;
	
	@LastModifiedDate
	private LocalDateTime updatedAt;
	
	@LastModifiedBy
	private String updatedBy;
	
	@Builder.Default
	@Relationship(type = "TENANT_OWNS_DOMAIN", direction = Relationship.Direction.OUTGOING)
	private Set<BusinessDomainNode> businessDomains = new HashSet<>();
}