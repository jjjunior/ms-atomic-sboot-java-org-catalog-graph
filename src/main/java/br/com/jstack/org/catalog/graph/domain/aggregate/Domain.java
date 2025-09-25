package br.com.jstack.org.catalog.graph.domain.aggregate;

import java.time.LocalDateTime;

import br.com.jstack.org.catalog.graph.domain.policy.shared.ValidationPolicy;
import br.com.jstack.org.catalog.graph.domain.vo.DomainStatus;
import br.com.jstack.org.catalog.graph.framework.adapter.output.node.TenantNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import static br.com.jstack.org.catalog.graph.domain.vo.OperationType.CREATE;
import static br.com.jstack.org.catalog.graph.domain.vo.OperationType.UPDATE;

/**
 * Aggregate Root: BusinessDomain<p>
 * <p>
 * Class domain that represents a canonical Business Domain in a multi-tenant environment.<p>
 * <p>
 * Lifecycle is explicit and controlled by commands (approve, reject, etc.).<br>
 * Every command returns a NEW instance.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "canonicalId")
@Builder(toBuilder = true)
public class Domain {
	private String        canonicalId;
	private String        tenantId;
	private String        acronym;
	private String        name;
	private String        description;
	private DomainStatus  status;
	private String        createdBy;
	private LocalDateTime createdAt;
	private String        updatedBy;
	private LocalDateTime updatedAt;
	private String        approvedBy;
	private LocalDateTime approvedAt;
	private String        rejectedBy;
	private LocalDateTime rejectedAt;
	private String        rejectionReason;
	private TenantNode    tenantCompanies;
	
	/**
	 * Factory for CREATE operation.<p>
	 * <p>
	 * Responsibilities:
	 * <ul>
	 *   <li>Derive {@code canonicalId} if missing ("domain:{tenantId}/{acronym}")</li>
	 *   <li>Force initial status to {@code PENDING_APPROVAL}</li>
	 *   <li>Initialize creation timestamp if missing</li>
	 *   <li>Clear approval and rejection audit fields</li>
	 *   <li>Delegate invariant validation to {@link ValidationPolicy}</li>
	 * </ul>
	 *
	 * @param draft  Pre-mapped instance (from DTO/mapper)
	 * @param policy Policy used to validate invariants
	 * @return New BusinessDomain instance in PENDING_APPROVAL
	 */
	public static Domain create(Domain draft, ValidationPolicy<Domain> policy) {
		String canonical = draft.getCanonicalId() != null
			? draft.getCanonicalId()
			: "domain:%s/%s".formatted(draft.getTenantId(), draft.getAcronym().toLowerCase());
		
		var domain = draft.toBuilder()
			.canonicalId(canonical)
			.status(DomainStatus.PENDING_APPROVAL)
			.createdAt(draft.getCreatedAt() != null ? draft.getCreatedAt() : LocalDateTime.now())
			.approvedAt(null).approvedBy(null)
			.rejectedAt(null).rejectedBy(null)
			.rejectionReason(null)
			.build();
		
		policy.validate(domain, CREATE);
		
		return domain;
	}
	
	/**
	 * Generic update for non-critical attributes.<p>
	 * <p>
	 * Responsibilities:
	 * <ul>
	 *   <li>Apply shallow changes from {@code changes} (ignores nulls)</li>
	 *   <li>Update audit fields (updatedBy, updatedAt)</li>
	 *   <li>Delegate validation to {@link ValidationPolicy}</li>
	 * </ul>
	 *
	 * <b>Note:</b> Use explicit commands for lifecycle changes.
	 */
	public Domain update(Domain changes, ValidationPolicy<Domain> policy) {
		var next = this.toBuilder()
			.name(changes.getName() != null ? changes.getName() : this.name)
			.description(changes.getDescription() != null ? changes.getDescription() : this.description)
			.updatedBy(changes.getUpdatedBy())
			.updatedAt(changes.getUpdatedAt() != null ? changes.getUpdatedAt() : LocalDateTime.now())
			.build();
		
		policy.validate(next, UPDATE);
		return next;
	}
	
	/**
	 * Approve domain: {@code PENDING_APPROVAL → ACTIVE}.<p>
	 * <p>
	 * Responsibilities:
	 * <ul>
	 *   <li>Set status to {@code ACTIVE}</li>
	 *   <li>Fill approval audit (approvedBy, approvedAt)</li>
	 *   <li>Clear rejection audit</li>
	 *   <li>Update audit fields</li>
	 *   <li>Validate transition using {@link ValidationPolicy}</li>
	 * </ul>
	 */
	public Domain approve(String actor, LocalDateTime when, ValidationPolicy<Domain> policy) {
		var instant = (when != null ? when : LocalDateTime.now());
		var next = this.toBuilder()
			.status(DomainStatus.ACTIVE)
			.approvedBy(actor).approvedAt(instant)
			.rejectedBy(null).rejectedAt(null).rejectionReason(null)
			.updatedBy(actor).updatedAt(instant)
			.build();
		
		policy.validate(next, UPDATE);
		return next;
	}
	
	/**
	 * Reject domain: {@code PENDING_APPROVAL → REJECTED}.<p>
	 * <p>
	 * Responsibilities:
	 * <ul>
	 *   <li>Set status to {@code REJECTED}</li>
	 *   <li>Fill rejection audit (rejectedBy, rejectedAt, reason)</li>
	 *   <li>Clear approval audit</li>
	 *   <li>Update audit fields</li>
	 *   <li>Validate transition using {@link ValidationPolicy}</li>
	 * </ul>
	 */
	public Domain reject(String actor, LocalDateTime when, String reason, ValidationPolicy<Domain> policy) {
		var instant = (when != null ? when : LocalDateTime.now());
		var next = this.toBuilder()
			.status(DomainStatus.REJECTED)
			.rejectedBy(actor).rejectedAt(instant).rejectionReason(reason)
			.approvedBy(null).approvedAt(null)
			.updatedBy(actor).updatedAt(instant)
			.build();
		
		policy.validate(next, UPDATE);
		return next;
	}
	
	/**
	 * Resubmit domain for approval: {@code REJECTED → PENDING_APPROVAL}.<p>
	 * <p>
	 * Responsibilities:
	 * <ul>
	 *   <li>Set status to {@code PENDING_APPROVAL}</li>
	 *   <li>Clear approval audit</li>
	 *   <li>Preserve rejectionReason for traceability (or clear per policy)</li>
	 *   <li>Update audit fields</li>
	 *   <li>Validate transition using {@link ValidationPolicy}</li>
	 * </ul>
	 */
	public Domain resubmitForApproval(String actor, LocalDateTime when, ValidationPolicy<Domain> policy) {
		var instant = (when != null ? when : LocalDateTime.now());
		var next = this.toBuilder()
			.status(DomainStatus.PENDING_APPROVAL)
			.approvedBy(null).approvedAt(null)
			.updatedBy(actor).updatedAt(instant)
			.build();
		
		policy.validate(next, UPDATE);
		return next;
	}
	
	/**
	 * Deprecate domain: typically {@code ACTIVE → DEPRECATED}.<p>
	 * <p>
	 * Responsibilities:
	 * <ul>
	 *   <li>Set status to {@code DEPRECATED}</li>
	 *   <li>Update audit fields</li>
	 *   <li>Validate transition using {@link ValidationPolicy}</li>
	 * </ul>
	 */
	public Domain deprecate(String actor, LocalDateTime when, ValidationPolicy<Domain> policy) {
		var instant = (when != null ? when : LocalDateTime.now());
		var next = this.toBuilder()
			.status(DomainStatus.DEPRECATED)
			.updatedBy(actor).updatedAt(instant)
			.build();
		policy.validate(next, UPDATE);
		return next;
	}
	
	/**
	 * Inactivate domain: typically {@code ACTIVE/DEPRECATED → INACTIVE}.<p>
	 * <p>
	 * Responsibilities:
	 * <ul>
	 *   <li>Set status to {@code INACTIVE}</li>
	 *   <li>Update audit fields</li>
	 *   <li>Validate transition using {@link ValidationPolicy}</li>
	 * </ul>
	 */
	public Domain inactivate(String actor, LocalDateTime when, ValidationPolicy<Domain> policy) {
		var instant = (when != null ? when : LocalDateTime.now());
		var next = this.toBuilder()
			.status(DomainStatus.INACTIVE)
			.updatedBy(actor).updatedAt(instant)
			.build();
		policy.validate(next, UPDATE);
		return next;
	}
	
	/**
	 * Activate domain: allowed transitions depend on policy.<br>
	 * For example: {@code PENDING_APPROVAL → ACTIVE} or {@code INACTIVE → ACTIVE}.<p>
	 * <p>
	 * Responsibilities:
	 * <ul>
	 *   <li>Set status to {@code ACTIVE}</li>
	 *   <li>Update audit fields</li>
	 *   <li>Validate transition using {@link ValidationPolicy}</li>
	 * </ul>
	 */
	public Domain activate(String actor, LocalDateTime when, ValidationPolicy<Domain> policy) {
		var instant = (when != null ? when : LocalDateTime.now());
		var next = this.toBuilder()
			.status(DomainStatus.ACTIVE)
			.updatedBy(actor).updatedAt(instant)
			.build();
		policy.validate(next, UPDATE);
		return next;
	}
}