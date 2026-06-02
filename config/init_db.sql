DROP TABLE IF EXISTS section;
DROP TABLE IF EXISTS contact;
DROP TABLE IF EXISTS resume;

CREATE TABLE public.resume
(
    uuid      CHAR(36) PRIMARY KEY,
    full_name TEXT NOT NULL
);

CREATE TABLE public.contact
(
    id          SERIAL PRIMARY KEY,
    resume_uuid CHAR(36) NOT NULL REFERENCES resume (uuid) ON DELETE CASCADE,
    type        TEXT     NOT NULL,
    value       TEXT     NOT NULL
);

CREATE UNIQUE INDEX contact_uuid_type_index
    ON public.contact (resume_uuid, type);

CREATE TABLE public.section
(
    id          SERIAL PRIMARY KEY,
    resume_uuid CHAR(36) NOT NULL REFERENCES resume (uuid) ON DELETE CASCADE,
    type        TEXT     NOT NULL,
    content       TEXT     NOT NULL
);

CREATE UNIQUE INDEX section_uuid_type_index
    ON public.section (resume_uuid, type);