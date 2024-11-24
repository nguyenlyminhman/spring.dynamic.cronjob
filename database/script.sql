-- public.cronjob definition
-- Drop table
-- DROP TABLE public.cronjob;

CREATE TABLE public.cronjob (
	"_id" bigserial NOT NULL,
	pattern varchar NULL,
	description varchar NULL,
	code varchar NULL
);

INSERT INTO public.cronjob
("_id", pattern, description, code)
VALUES(1, '0/3 * * * * *', 'Every 3 seconds', 'SCHEDULE_01');
INSERT INTO public.cronjob
("_id", pattern, description, code)
VALUES(2, '0/6 * * * * *', 'Every 5 seconds', 'SCHEDULE_02');
