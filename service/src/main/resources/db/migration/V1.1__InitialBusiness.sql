-- bubbles
CREATE TABLE bubbles (
	bubble_id bigint IDENTITY(1,1) NOT NULL,
	bubble_ext varchar(255) COLLATE Latin1_General_100_CI_AI_SC_UTF8 NOT NULL,
	user_ref varchar(255) COLLATE Latin1_General_100_CI_AI_SC_UTF8 NOT NULL,
	name varchar(255) COLLATE Latin1_General_100_CI_AI_SC_UTF8 NOT NULL,
	CONSTRAINT PK_bubble PRIMARY KEY (bubble_id),
	CONSTRAINT AK_bubble UNIQUE (bubble_ext)
);
CREATE NONCLUSTERED INDEX IDX_bubble_ak ON bubbles (bubble_ext);
CREATE NONCLUSTERED INDEX IDX_bubble_user ON bubbles (user_ref);

-- humans
CREATE TABLE humans (
	human_id bigint IDENTITY(1,1) NOT NULL,
	human_ext varchar(255) COLLATE Latin1_General_100_CI_AI_SC_UTF8 NOT NULL,
	user_ref varchar(255) COLLATE Latin1_General_100_CI_AI_SC_UTF8 NOT NULL,
	name varchar(255) COLLATE Latin1_General_100_CI_AI_SC_UTF8 NOT NULL,
	CONSTRAINT PK_human PRIMARY KEY (human_id),
	CONSTRAINT AK_human UNIQUE (human_ext)
);
CREATE NONCLUSTERED INDEX IDX_human_ak ON humans (human_ext);
CREATE NONCLUSTERED INDEX IDX_human_user ON humans (user_ref);
