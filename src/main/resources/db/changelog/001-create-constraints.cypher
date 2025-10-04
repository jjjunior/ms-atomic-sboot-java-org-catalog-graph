--liquibase formatted cypher

--changeset jota:001 labels:meta context:base
CREATE CONSTRAINT tenant_id_unique IF NOT EXISTS
FOR (t:Tenant) REQUIRE t.tenantId IS UNIQUE;

--rollback DROP CONSTRAINT tenant_id_unique IF EXISTS;