package br.com.jstack.org.catalog.graph.domain.policy;

import br.com.jstack.org.catalog.graph.application.port.output.BusinessDomainOutputPort;
import br.com.jstack.org.catalog.graph.domain.model.BusinessDomain;
import br.com.jstack.org.catalog.graph.domain.specification.CanonicalMatchesSpec;
import br.com.jstack.org.catalog.graph.domain.specification.SpecificationPolicyAdapter;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DomainPolicyFactory {
	
	public Policy<BusinessDomain> invariants(BusinessDomainOutputPort output) {
		var spec = CanonicalMatchesSpec.INSTANCE;
//		var spec2 = new SpecificationPolicyAdapter(new UniqueAcronymPerTenantPolicy(output));
		
		return SpecificationPolicyAdapter.from(
			spec,
			() -> new IllegalArgumentException("Domain invariants failed")
		);
	}
}