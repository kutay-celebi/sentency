package tr.com.nekasoft.sentency.api.external.linguarobot;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tr.com.nekasoft.sentency.api.data.word.SynonymAntonym;
import tr.com.nekasoft.sentency.api.entity.Word;
import tr.com.nekasoft.sentency.api.entity.WordDefinition;
import tr.com.nekasoft.sentency.api.entity.WordDefinitionExamples;
import tr.com.nekasoft.sentency.api.entity.WordSynonymAntonym;
import tr.com.nekasoft.sentency.api.exception.ExceptionCode;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LrResponse implements Serializable {

  private static final long serialVersionUID = -407244655555366330L;

  private List<LrEntry> entries;

  public Set<WordDefinition> toDefinitions(Word word) {

    if (entries == null) {
      throw ExceptionCode.DATA_NOT_FOUND.toException();
    }

    return entries.stream().flatMap(entry -> {
      if (entry.getLexemes() == null) {
        throw ExceptionCode.DATA_NOT_FOUND.toException();
      }

      return entry.getLexemes().stream();
    }).flatMap(lexeme -> {
      if (lexeme.getSenses() == null) {
        throw ExceptionCode.DATA_NOT_FOUND.toException();
      }

      return lexeme.getSenses().stream().flatMap(sense -> {

        if (sense.getSubSenses() != null) {
          return sense.getSubSenses().stream().map(subSense -> mapSenseToDefinition(word, lexeme, subSense, sense));
        }

        return Stream.of(mapSenseToDefinition(word, lexeme, sense, null));
      });
    }).collect(Collectors.toSet());
  }

  private static WordDefinition mapSenseToDefinition(Word word, LrLexemes lexeme, LrSense sense, LrSense parent) {
    WordDefinition definition = WordDefinition
        .builder()
        .word(word)
        .definition(sense.getDefinition())
        .definitionOf(parent != null ? parent.getDefinition() : null)
        .partOfSpeech(lexeme.getPartOfSpeech())
        .build();

    if (lexeme.getSynonymSets() != null) {
      Set<WordSynonymAntonym> synonyms = lexeme
          .getSynonymSets()
          .stream()
          .map(synonym -> WordSynonymAntonym
              .builder()
              .wordDefinition(definition)
              .type(SynonymAntonym.SYNONYM)
              .definition(synonym.getSense())
              .word(String.join(",", synonym.getSynonyms()))
              .build())
          .collect(Collectors.toSet());
      definition.getSynonymAntonym().addAll(synonyms);
    }

    if (lexeme.getAntonymSets() != null) {
      Set<WordSynonymAntonym> antonyms = lexeme
          .getAntonymSets()
          .stream()
          .map(antonym -> WordSynonymAntonym
              .builder()
              .wordDefinition(definition)
              .type(SynonymAntonym.ANTONYM)
              .definition(antonym.getSense())
              .word(String.join(",", antonym.getAntonyms()))
              .build())
          .collect(Collectors.toSet());
      definition.getSynonymAntonym().addAll(antonyms);
    }

    if (sense.getUsageExamples() != null) {
      Set<WordDefinitionExamples> examples = sense
          .getUsageExamples()
          .stream()
          .map(example -> WordDefinitionExamples.builder().example(example).definition(definition).build())
          .collect(Collectors.toSet());
      definition.setExamples(examples);
    }
    return definition;
  }
}
