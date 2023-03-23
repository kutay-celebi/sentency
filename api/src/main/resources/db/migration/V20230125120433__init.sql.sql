CREATE TABLE snt_word
(
    id         VARCHAR(255) NOT NULL,
    deleted    BOOLEAN,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    deleted_at TIMESTAMP WITHOUT TIME ZONE,
    word       VARCHAR(255),
    CONSTRAINT pk_snt_word PRIMARY KEY (id)
);

CREATE TABLE snt_word_definition
(
    id             VARCHAR(255) NOT NULL,
    deleted        BOOLEAN,
    created_at     TIMESTAMP WITHOUT TIME ZONE,
    deleted_at     TIMESTAMP WITHOUT TIME ZONE,
    definition     VARCHAR(4000),
    definition_tr  VARCHAR(4000),
    part_of_speech VARCHAR(255),
    synonyms       VARCHAR(255),
    word_id        VARCHAR(255),
    CONSTRAINT pk_snt_word_definition PRIMARY KEY (id)
);

CREATE TABLE snt_word_definition_example
(
    id            VARCHAR(255) NOT NULL,
    deleted       BOOLEAN,
    created_at    TIMESTAMP WITHOUT TIME ZONE,
    deleted_at    TIMESTAMP WITHOUT TIME ZONE,
    example       VARCHAR(255),
    definition_id VARCHAR(255),
    CONSTRAINT pk_snt_word_definition_example PRIMARY KEY (id)
);

CREATE TABLE snt_user
(
    id         VARCHAR(255) NOT NULL,
    deleted    BOOLEAN,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    deleted_at TIMESTAMP WITHOUT TIME ZONE,
    username   VARCHAR(255),
    password   VARCHAR(255),
    role       VARCHAR(255),
    CONSTRAINT pk_snt_user PRIMARY KEY (id)
);

CREATE TABLE snt_sentence
(
    id           VARCHAR(255) NOT NULL,
    deleted      BOOLEAN,
    created_at   TIMESTAMP WITHOUT TIME ZONE,
    deleted_at   TIMESTAMP WITHOUT TIME ZONE,
    sentence     VARCHAR(4000),
    sentence_tr  VARCHAR(4000),
    user_word_id VARCHAR(255),
    CONSTRAINT pk_snt_sentence PRIMARY KEY (id)
);

CREATE TABLE snt_user_word
(
    id           VARCHAR(255) NOT NULL,
    deleted      BOOLEAN,
    created_at   TIMESTAMP WITHOUT TIME ZONE,
    deleted_at   TIMESTAMP WITHOUT TIME ZONE,
    user_id      VARCHAR(255),
    word_id      VARCHAR(255),
    difficulty   VARCHAR(255),
    next_review  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    last_review  TIMESTAMP WITHOUT TIME ZONE,
    review_count BIGINT,
    CONSTRAINT pk_snt_user_word PRIMARY KEY (id)
);

ALTER TABLE snt_user_word
    ADD CONSTRAINT FK_SNT_USER_WORD_ON_USER FOREIGN KEY (user_id) REFERENCES snt_user (id);

ALTER TABLE snt_user_word
    ADD CONSTRAINT FK_SNT_USER_WORD_ON_WORD FOREIGN KEY (word_id) REFERENCES snt_word (id);

ALTER TABLE snt_sentence
    ADD CONSTRAINT FK_SNT_SENTENCE_ON_USER_WORD FOREIGN KEY (user_word_id) REFERENCES snt_user_word (id);

ALTER TABLE snt_word_definition
    ADD CONSTRAINT FK_SNT_WORD_DEFINITION_ON_WORD FOREIGN KEY (word_id) REFERENCES snt_word (id);

ALTER TABLE snt_word_definition_example
    ADD CONSTRAINT FK_SNT_WORD_DEFINITION_EXAMPLE_ON_DEFINITION FOREIGN KEY (definition_id) REFERENCES snt_word_definition (id);
