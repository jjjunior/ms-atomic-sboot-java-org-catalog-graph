--liquibase formatted cypher

--changeset jota:001
CREATE CONSTRAINT domain_name_unique IF NOT EXISTS
FOR (d:Domain) REQUIRE d.name IS UNIQUE;

--changeset jota:002
MERGE (d:Domain {id:'dom:financeiro', name:'Financeiro'});