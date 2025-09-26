package br.com.jstack.org.catalog.graph.domain.vo;

import lombok.Value;

@Value(staticConstructor = "of")
public class TenantId {
	String value;
}