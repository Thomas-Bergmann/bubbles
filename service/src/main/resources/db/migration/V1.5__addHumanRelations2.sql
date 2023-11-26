drop table human_relations;
-- human relations
CREATE TABLE relation_fix (
	human_rel_id bigint IDENTITY(1,1) NOT NULL,
	human_1 bigint NOT NULL,
	human_2 bigint NOT NULL,
	type varchar(255) COLLATE Latin1_General_100_CI_AI_SC_UTF8 NOT NULL,
	CONSTRAINT PK_relation_fix PRIMARY KEY (human_rel_id),
	CONSTRAINT AK_relation_fix UNIQUE (human_1, human_2, type)
);
CREATE NONCLUSTERED INDEX IDX_relation_fix_1 ON relation_fix (human_1);
CREATE NONCLUSTERED INDEX IDX_relation_fix_2 ON relation_fix (human_2);

CREATE TABLE relation_flex (
	human_rel_id bigint IDENTITY(1,1) NOT NULL,
	human_1 bigint NOT NULL,
	human_2 bigint NOT NULL,
	type varchar(255) COLLATE Latin1_General_100_CI_AI_SC_UTF8 NOT NULL,
	date_start varchar(10) COLLATE Latin1_General_100_CI_AI_SC_UTF8 NULL,
	date_end varchar(10) COLLATE Latin1_General_100_CI_AI_SC_UTF8 NULL,
	CONSTRAINT PK_relation_flex PRIMARY KEY (human_rel_id),
	CONSTRAINT AK_relation_flex UNIQUE (human_1, human_2, type)
);
CREATE NONCLUSTERED INDEX IDX_relation_flex_1 ON relation_flex (human_1);
CREATE NONCLUSTERED INDEX IDX_relation_flex_2 ON relation_flex (human_2);
