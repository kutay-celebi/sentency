CREATE TABLE snt_word_definition_phrases
(
    id                 VARCHAR(255) NOT NULL,
    deleted            BOOLEAN,
    created_at         TIMESTAMP WITHOUT TIME ZONE,
    deleted_at         TIMESTAMP WITHOUT TIME ZONE,
    lang               VARCHAR(255),
    part_of_speech     VARCHAR(255),
    definition         VARCHAR(255),
    definition_of      VARCHAR(255),
    word_definition_id VARCHAR(255),
    CONSTRAINT pk_snt_word_definition_phrases PRIMARY KEY (id)
);

ALTER TABLE snt_word_definition_phrases
    ADD CONSTRAINT FK_SNT_WORD_DEFINITION_PHRASES_ON_WORD_DEFINITION FOREIGN KEY (word_definition_id) REFERENCES snt_word_definition (id);
