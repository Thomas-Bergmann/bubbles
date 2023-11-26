-- human relations
CREATE TABLE human_relations (
	human_rel_id bigint IDENTITY(1,1) NOT NULL,
	human_1 bigint NOT NULL,
	human_2 bigint NOT NULL,
	type varchar(255) COLLATE Latin1_General_100_CI_AI_SC_UTF8 NOT NULL,
	CONSTRAINT PK_human_rel PRIMARY KEY (human_rel_id),
	CONSTRAINT AK_human_rel UNIQUE (human_1, human_2, type)
);
CREATE NONCLUSTERED INDEX IDX_human_rel_1 ON human_relations (human_1);
CREATE NONCLUSTERED INDEX IDX_human_rel_2 ON human_relations (human_2);
