package br.com.jstack.org.catalog.graph.domain.node;

import java.time.LocalDateTime;

import br.com.jstack.org.catalog.graph.domain.vo.DomainStatus;
import lombok.AllArgsConstructor;
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
@Node("BusinessDomain")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "canonicalId")
public class BusinessDomainNode {
	
	@Id
	private String canonicalId;
	
	private String tenantId;
	
	private String acronym;
	
	private String name;
	
	private String description;
	
	private DomainStatus status;
	
	private String approvedBy;
	
	private LocalDateTime approvedAt;
	
	private String rejectedBy;
	
	private LocalDateTime rejectedAt;
	
	private String rejectionReason;
	
	@CreatedDate
	private LocalDateTime createdAt;
	
	@CreatedBy
	private String createdBy;
	
	@LastModifiedDate
	private LocalDateTime updatedAt;
	
	@LastModifiedBy
	private String updatedBy;
	
	@Relationship(type = "TENANT_OWNS_DOMAIN", direction = Relationship.Direction.INCOMING)
	private TenantCompanyNode tenant;
}