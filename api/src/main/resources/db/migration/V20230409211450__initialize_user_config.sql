CREATE TABLE snt_user_config
(
    id              VARCHAR(255) NOT NULL,
    deleted         BOOLEAN,
    created_at      TIMESTAMP WITHOUT TIME ZONE,
    deleted_at      TIMESTAMP WITHOUT TIME ZONE,
    target_language VARCHAR(255),
    user_id         VARCHAR(255),
    CONSTRAINT pk_snt_user_config PRIMARY KEY (id)
);

ALTER TABLE snt_user_config
    ADD CONSTRAINT FK_SNT_USER_CONFIG_ON_USER FOREIGN KEY (user_id) REFERENCES snt_user (id);
