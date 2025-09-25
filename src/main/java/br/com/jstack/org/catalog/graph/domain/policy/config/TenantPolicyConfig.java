package br.com.jstack.org.catalog.graph.domain.policy.config;

import br.com.jstack.org.catalog.graph.domain.aggregate.Tenant;
import br.com.jstack.org.catalog.graph.domain.policy.TenantPolicyFactory;
import br.com.jstack.org.catalog.graph.domain.policy.shared.ValidationPolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TenantPolicyConfig {
	
	@Bean
	ValidationPolicy<Tenant> tenantCreatePolicy(TenantPolicyFactory f) {
		return f.createPolicy();
	}
	
	@Bean ValidationPolicy<Tenant> tenantUpdatePolicy(TenantPolicyFactory f) {
		return f.updatePolicy();
	}
}