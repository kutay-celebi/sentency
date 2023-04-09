alter table if exists snt_word_definition
    add if not exists definition_of varchar(255);

alter table if exists snt_word_definition
    drop column if exists synonyms;

CREATE TABLE if not exists snt_word_synonym_antonym
(
    id                 VARCHAR(255) NOT NULL,
    deleted            BOOLEAN,
    created_at         TIMESTAMP WITHOUT TIME ZONE,
    deleted_at         TIMESTAMP WITHOUT TIME ZONE,
    definition         VARCHAR(255),
    word               VARCHAR(255),
    type               VARCHAR(255),
    word_definition_id VARCHAR(255),
    CONSTRAINT pk_snt_word_synonym_antonym PRIMARY KEY (id)
);

ALTER TABLE if exists snt_word_synonym_antonym
    ADD CONSTRAINT FK_SNT_WORD_SYNONYM_ANTONYM_ON_WORD_DEFINITION FOREIGN KEY (word_definition_id) REFERENCES snt_word_definition (id);
