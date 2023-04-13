alter table snt_word_definition_phrases
    alter column definition type varchar(4000) using definition::varchar(4000);

alter table snt_word_definition_phrases
    alter column definition_of type varchar(4000) using definition_of::varchar(4000);

alter table snt_word_definition_example
    alter column example type varchar(4000) using example::varchar(4000);
