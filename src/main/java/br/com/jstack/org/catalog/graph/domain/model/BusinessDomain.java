package br.com.jstack.org.catalog.graph.domain.model;

import java.time.LocalDateTime;

import br.com.jstack.org.catalog.graph.domain.policy.ValidationPolicy;
import br.com.jstack.org.catalog.graph.domain.vo.DomainStatus;
import lombok.Builder;
import lombok.With;

import static br.com.jstack.org.catalog.graph.domain.vo.OperationType.CREATE;
import static br.com.jstack.org.catalog.graph.domain.vo.OperationType.UPDATE;

/**
 * Aggregate Root: BusinessDomain<p>
 * <p>
 * Immutable domain record that represents a canonical Business Domain in a multi-tenant environment.<p>
 * <p>
 * Lifecycle is explicit and controlled by commands (approve, reject, etc.).<br>
 * Every command returns a NEW instance (immutability principle).
 */
@Builder(toBuilder = true)
public record BusinessDomain(
	String tenantId,
	String canonicalId,
	String acronym,
	String name,
	String description,
	DomainStatus status,
	String createdBy,
	@With
	String updatedBy,
	String approvedBy,
	String rejectedBy,
	LocalDateTime createdAt,
	@With
	LocalDateTime updatedAt,
	LocalDateTime approvedAt,
	LocalDateTime rejectedAt,
	@With
	String rejectionReason
) {
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
	public static BusinessDomain create(BusinessDomain draft, ValidationPolicy<BusinessDomain> policy) {
		String canonical = draft.canonicalId() != null
			? draft.canonicalId()
			: "domain:%s/%s".formatted(draft.tenantId(), draft.acronym().toLowerCase());
		
		var domain = draft.toBuilder()
			.canonicalId(canonical)
			.status(DomainStatus.PENDING_APPROVAL)
			.createdAt(draft.createdAt() != null ? draft.createdAt() : LocalDateTime.now())
			.approvedAt(null).approvedBy(null)
			.rejectedAt(null).rejectedBy(null)
			.rejectionReason(null)
			.build();
		
		policy.validate(domain,CREATE);
		
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
	public BusinessDomain update(BusinessDomain changes, ValidationPolicy<BusinessDomain> policy) {
		var next = this.toBuilder()
			.name(changes.name() != null ? changes.name() : this.name)
			.description(changes.description() != null ? changes.description() : this.description)
			.updatedBy(changes.updatedBy())
			.updatedAt(changes.updatedAt() != null ? changes.updatedAt() : LocalDateTime.now())
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
	public BusinessDomain approve(String actor, LocalDateTime when, ValidationPolicy<BusinessDomain> policy) {
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
	public BusinessDomain reject(String actor, LocalDateTime when, String reason,
	                             ValidationPolicy<BusinessDomain> policy) {
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
	public BusinessDomain resubmitForApproval(String actor, LocalDateTime when,
	                                          ValidationPolicy<BusinessDomain> policy) {
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
	public BusinessDomain deprecate(String actor, LocalDateTime when, ValidationPolicy<BusinessDomain> policy) {
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
	public BusinessDomain inactivate(String actor, LocalDateTime when, ValidationPolicy<BusinessDomain> policy) {
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
	public BusinessDomain activate(String actor, LocalDateTime when, ValidationPolicy<BusinessDomain> policy) {
		var instant = (when != null ? when : LocalDateTime.now());
		var next = this.toBuilder()
			.status(DomainStatus.ACTIVE)
			.updatedBy(actor).updatedAt(instant)
			.build();
		policy.validate(next, UPDATE);
		return next;
	}
}