package tr.com.nekasoft.sentency.api.external.linguarobot;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tr.com.nekasoft.sentency.api.data.word.SynonymAntonym;
import tr.com.nekasoft.sentency.api.entity.WordDefinition;
import tr.com.nekasoft.sentency.api.exception.BusinessException;
import tr.com.nekasoft.sentency.api.exception.ExceptionCode;

class LrResponseTest {

  private final String word = "word";
  private final String example = "example";
  private final String definition = "definition";
  private final String partOfSpeech = "partOfSpeech";

  private LrSense sense;
  private LrLexemes lexeme;
  private LrEntry entry;
  private LrResponse instance;

  @BeforeEach
  void setUp() {

    sense = LrSense.builder().definition(definition).usageExamples(Collections.singletonList(example)).build();
    lexeme = LrLexemes
        .builder()
        .lemma(word)
        .partOfSpeech(partOfSpeech)
        .senses(Collections.singletonList(sense))
        .build();
    entry = LrEntry.builder().entry(word).lexemes(Collections.singletonList(lexeme)).build();
    instance = LrResponse.builder().entries(Collections.singletonList(entry)).build();
  }

  @Test
  void mapRootFields() {
    // given

    // when
    Set<WordDefinition> actual = instance.toDefinitions();

    // then
    assertThat(actual, hasSize(1));
    assertThat(actual, hasItem(hasProperty("definition", equalTo(definition))));
    assertThat(actual, hasItem(hasProperty("partOfSpeech", equalTo(partOfSpeech))));

  }

  @Test
  void examplesShouldBeMapped() {
    // given
    sense = LrSense.builder().definition(definition).usageExamples(List.of(example, example)).build();
    lexeme = LrLexemes
        .builder()
        .lemma(word)
        .partOfSpeech(partOfSpeech)
        .senses(Collections.singletonList(sense))
        .build();
    entry = LrEntry.builder().entry(word).lexemes(Collections.singletonList(lexeme)).build();
    instance = LrResponse.builder().entries(Collections.singletonList(entry)).build();

    // when
    Set<WordDefinition> actual = instance.toDefinitions();

    // then
    assertThat(actual, hasItem(hasProperty("examples", hasSize(2))));
    assertThat(actual, hasItem(hasProperty("examples", everyItem(hasProperty("example", equalTo(example))))));
  }

  @Test
  void synonymsShouldBeMapped() {
    // given
    LrSynonymAntonym synonymAntonym = LrSynonymAntonym
        .builder()
        .sense("synonym-sense")
        .synonyms(List.of("synonym", "synonym1"))
        .build();
    LrSynonymAntonym synonymAntonym1 = LrSynonymAntonym
        .builder()
        .sense("antonym-sense")
        .antonyms(List.of("antonym", "antonym1"))
        .build();

    lexeme = LrLexemes
        .builder()
        .lemma(word)
        .partOfSpeech(partOfSpeech)
        .synonymSets(Set.of(synonymAntonym))
        .antonymSets(Set.of(synonymAntonym1))
        .senses(Collections.singletonList(sense))
        .build();
    entry = LrEntry.builder().entry(word).lexemes(Collections.singletonList(lexeme)).build();
    instance = LrResponse.builder().entries(Collections.singletonList(entry)).build();

    // when
    Set<WordDefinition> actual = instance.toDefinitions();

    // then
    assertThat(actual,
        hasItem(hasProperty("synonymAntonym", hasItem(hasProperty("type", equalTo(SynonymAntonym.SYNONYM))))));
    assertThat(actual,
        hasItem(hasProperty("synonymAntonym", hasItem(hasProperty("word", equalTo("synonym,synonym1"))))));
    assertThat(actual,
        hasItem(hasProperty("synonymAntonym", hasItem(hasProperty("definition", equalTo("synonym-sense"))))));

    assertThat(actual,
        hasItem(hasProperty("synonymAntonym", hasItem(hasProperty("type", equalTo(SynonymAntonym.ANTONYM))))));
    assertThat(actual,
        hasItem(hasProperty("synonymAntonym", hasItem(hasProperty("word", equalTo("antonym,antonym1"))))));
    assertThat(actual,
        hasItem(hasProperty("synonymAntonym", hasItem(hasProperty("definition", equalTo("antonym-sense"))))));
  }

  @Test
  void subSensesShouldBeMapped() {
    // given
    LrSense subSense = LrSense.builder().definition("sub").build();
    sense = LrSense.builder().definition("parent").subSenses(Collections.singletonList(subSense)).build();

    lexeme = LrLexemes
        .builder()
        .lemma(word)
        .partOfSpeech(partOfSpeech)
        .synonymSets(Collections.emptySet())
        .antonymSets(Collections.emptySet())
        .senses(Collections.singletonList(sense))
        .build();
    entry = LrEntry.builder().entry(word).lexemes(Collections.singletonList(lexeme)).build();
    instance = LrResponse.builder().entries(Collections.singletonList(entry)).build();

    // when
    Set<WordDefinition> actual = instance.toDefinitions();

    // then
    assertThat(actual, hasItem(hasProperty("definition", equalTo("sub"))));
    assertThat(actual, hasItem(hasProperty("definitionOf", equalTo("parent"))));

  }

  @Test
  void entryIsNull() {
    // given
    instance = LrResponse.builder().entries(null).build();

    // when
    BusinessException actual = Assertions.assertThrows(BusinessException.class, () -> instance.toDefinitions());

    // then
    assertThat(actual.getCode(), equalTo(ExceptionCode.DATA_NOT_FOUND.getCode()));
  }

  @Test
  void lexemeIsNull() {
    // given
    entry = LrEntry.builder().entry(word).lexemes(null).build();
    instance = LrResponse.builder().entries(Collections.singletonList(entry)).build();

    // when
    BusinessException actual = Assertions.assertThrows(BusinessException.class, () -> instance.toDefinitions());

    // then
    assertThat(actual.getCode(), equalTo(ExceptionCode.DATA_NOT_FOUND.getCode()));
  }

  @Test
  void sensesIsNull() {
    // given
    lexeme = LrLexemes.builder().lemma(word).partOfSpeech(partOfSpeech).senses(null).build();
    entry = LrEntry.builder().entry(word).lexemes(Collections.singletonList(lexeme)).build();
    instance = LrResponse.builder().entries(Collections.singletonList(entry)).build();

    // when
    BusinessException actual = Assertions.assertThrows(BusinessException.class, () -> instance.toDefinitions());

    // then
    assertThat(actual.getCode(), equalTo(ExceptionCode.DATA_NOT_FOUND.getCode()));
  }

  @Test
  void synonymsIsNull() {
    // given
    sense = LrSense.builder().definition(definition).usageExamples(null).build();
    lexeme = LrLexemes
        .builder()
        .lemma(word)
        .partOfSpeech(partOfSpeech)
        .synonymSets(null)
        .antonymSets(null)
        .senses(Collections.singletonList(sense))
        .build();
    entry = LrEntry.builder().entry(word).lexemes(Collections.singletonList(lexeme)).build();
    instance = LrResponse.builder().entries(Collections.singletonList(entry)).build();

    // when
    Set<WordDefinition> actual = instance.toDefinitions();

    // then
    assertThat(actual, hasItem(hasProperty("synonymAntonym", empty())));
  }

  @Test
  void examplesIsNull() {
    // given
    sense = LrSense.builder().definition(definition).usageExamples(null).build();
    lexeme = LrLexemes
        .builder()
        .lemma(word)
        .partOfSpeech(partOfSpeech)
        .senses(Collections.singletonList(sense))
        .build();
    entry = LrEntry.builder().entry(word).lexemes(Collections.singletonList(lexeme)).build();
    instance = LrResponse.builder().entries(Collections.singletonList(entry)).build();

    // when
    Set<WordDefinition> actual = instance.toDefinitions();

    // then
    assertThat(actual, hasItem(hasProperty("examples", empty())));
  }

}
