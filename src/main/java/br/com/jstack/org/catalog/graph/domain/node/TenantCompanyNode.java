package br.com.jstack.org.catalog.graph.domain.node;

import java.time.LocalDateTime;

import br.com.jstack.org.catalog.graph.domain.model.TenantCompany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Data
@Node("TenantCompany")
@AllArgsConstructor
@NoArgsConstructor
public class TenantCompanyNode {
	
	@Id
	private String tenantId;
	private String name;
	private Status status;
	
	@CreatedDate
	private LocalDateTime createdAt;
	
	@CreatedBy
	private String createdBy;
	
	@LastModifiedDate
	private LocalDateTime updatedAt;
	
	@LastModifiedBy
	private String updatedBy;
	
	public enum Status { ACTIVE, INACTIVE }
}