package br.com.jstack.org.catalog.graph.domain.specification;


import br.com.jstack.org.catalog.graph.domain.model.BusinessDomain;

public enum CanonicalMatchesSpec implements Specification<BusinessDomain> {
	INSTANCE;
	@Override public boolean isSatisfiedBy(BusinessDomain d) {
		var expected = "domain:" + d.getTenantId() + "/" + d.getAcronym();
		return expected.equals(d.getCanonicalId());
	}
}