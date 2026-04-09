CREATE TABLE IF NOT EXISTS public.resume
(
    uuid      char(36) NOT NULL
        CONSTRAINT resume_pk
            PRIMARY KEY,
    full_name text     NOT NULL
);

CREATE TABLE IF NOT EXISTS public.contact
(
    id          integer GENERATED ALWAYS AS IDENTITY
        CONSTRAINT contact_pk
            PRIMARY KEY,
    resume_uuid char(36)
        CONSTRAINT contact_resume_uuid_fk
            REFERENCES resume
            ON DELETE CASCADE,
    type        text NOT NULL,
    value       text NOT NULL

);
CREATE UNIQUE INDEX IF NOT EXISTS contact_uuid_type_index
    ON public.contact (resume_uuid, type);