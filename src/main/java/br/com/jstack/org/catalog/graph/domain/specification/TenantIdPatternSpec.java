package br.com.jstack.org.catalog.graph.domain.specification;

import br.com.jstack.org.catalog.graph.domain.vo.TenantId;

public enum TenantIdPatternSpec implements Specification<TenantId> {
	INSTANCE;
	private static final String PATTERN = "^[a-z0-9-]{2,10}$";
	
	public boolean isSatisfiedBy(TenantId id) {
		return id != null && id.getValue() != null && id.getValue().matches(PATTERN);
	}
}