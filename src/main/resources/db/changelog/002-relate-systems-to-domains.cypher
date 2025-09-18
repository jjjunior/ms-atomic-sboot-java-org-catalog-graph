--liquibase formatted cypher

--changeset jota:003
MERGE (s:System {acronym:'HUBM', tenantId:'vivo'})
MERGE (d:Domain {id:'dom:mensageria', name:'Mensageria'})
MERGE (s)-[:BELONGS_TO]->(d);