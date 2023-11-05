-- bubbles
CREATE TABLE bubbles (
	bubble_id bigint IDENTITY(1,1) NOT NULL,
	user_ref varchar(255) COLLATE Latin1_General_100_CI_AI_SC_UTF8 NOT NULL,
	abbreviation varchar(255) COLLATE Latin1_General_100_CI_AI_SC_UTF8 NOT NULL,
	name varchar(255) COLLATE Latin1_General_100_CI_AI_SC_UTF8 NOT NULL,
	CONSTRAINT PK_bubble PRIMARY KEY (bubble_id),
	CONSTRAINT AK_bubble UNIQUE (abbreviation)
);
CREATE NONCLUSTERED INDEX IDX_bubble_ak ON bubbles (abbreviation);
CREATE NONCLUSTERED INDEX IDX_bubble_user ON bubbles (user_ref);
