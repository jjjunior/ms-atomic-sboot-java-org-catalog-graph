package br.com.jstack.org.catalog.graph.config;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.neo4j.config.EnableNeo4jAuditing;

@Configuration
@EnableNeo4jAuditing
public class Neo4jAuditingConfig {
	@Bean
	AuditorAware<String> auditorAware() {
//		return () -> Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
//			.map(Authentication::getName)
//			.or(() -> Optional.of("system"));
		
		return () -> Optional.of("system");
	}
}