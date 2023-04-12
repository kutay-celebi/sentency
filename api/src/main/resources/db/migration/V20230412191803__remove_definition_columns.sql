alter table snt_word_definition
    drop column if exists definition;

alter table snt_word_definition
    drop column if exists definition_tr;

alter table snt_word_definition
    drop column if exists part_of_speech;

