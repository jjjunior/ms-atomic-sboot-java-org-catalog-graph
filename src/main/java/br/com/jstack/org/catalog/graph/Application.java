package br.com.jstack.org.catalog.graph;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.neo4j.repository.config.EnableReactiveNeo4jRepositories;

@EnableReactiveNeo4jRepositories(basePackages = "br.com.jstack.org.catalog.graph.framework.adapter.output.repository")
@SpringBootApplication
public class Application {
	
	static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
}
