package br.com.jstack.org.catalog.graph.domain.vo;

/**
 * Domain lifecycle states:
 * <ul>
 *   <li>{@code PENDING_APPROVAL}: Created but not yet approved</li>
 *   <li>{@code ACTIVE}: Approved and usable</li>
 *   <li>{@code DEPRECATED}: Being phased out but still referenced</li>
 *   <li>{@code INACTIVE}: Retired or blocked</li>
 *   <li>{@code REJECTED}: Explicitly rejected during approval process</li>
 * </ul>
 */
public enum DomainStatus {
	PENDING_APPROVAL, ACTIVE, DEPRECATED, INACTIVE, REJECTED
}
