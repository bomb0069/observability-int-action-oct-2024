CREATE TABLE "users" (
"id" SERIAL NOT NULL,
"org_id" integer NOT NULL,
"name" character varying NOT NULL,
"created" TIMESTAMP DEFAULT now(),
"updated" TIMESTAMP DEFAULT now()
);

INSERT INTO users (org_id, "name", created, updated) VALUES(1, 'Somchai', now(), now());
INSERT INTO users (org_id, "name", created, updated) VALUES(1, 'Ekkasit', now(), now());
INSERT INTO users (org_id, "name", created, updated) VALUES(1, 'Boonchuay', now(), now());
