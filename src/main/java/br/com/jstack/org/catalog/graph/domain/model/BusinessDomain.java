package br.com.jstack.org.catalog.graph.domain.model;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder(toBuilder = true)
public class BusinessDomain {
	@EqualsAndHashCode.Include
	private String canonicalId;
	private String tenantId;
	private String acronym;
	private String name;
	private String description;
	private enum Status { ACTIVE, DEPRECATED, INACTIVE }
	private LocalDateTime createdAt;
	private String createdBy;
	private LocalDateTime updatedAt;
	private String updatedBy;
}