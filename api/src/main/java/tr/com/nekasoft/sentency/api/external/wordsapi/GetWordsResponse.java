package tr.com.nekasoft.sentency.api.external.wordsapi;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tr.com.nekasoft.sentency.api.entity.Word;
import tr.com.nekasoft.sentency.api.entity.WordDefinition;
import tr.com.nekasoft.sentency.api.entity.WordDefinitionExamples;
import tr.com.nekasoft.sentency.api.exception.ExceptionCode;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetWordsResponse implements Serializable {

  private static final long serialVersionUID = 15371055057558225L;

  private List<Definition> results;
  private BigDecimal frequency;

  public Set<WordDefinition> toWordDefinitionEntity(Word word) {

    if (results == null) {
      throw ExceptionCode.DATA_NOT_FOUND.toException(Map.of("word", word.getWord()));
    }

    return results.stream().map(def -> {
      WordDefinition wordDefinition = WordDefinition
          .builder()
          .word(word)
          .partOfSpeech(def.getPartOfSpeech())
          .definition(def.getDefinition())
          .build();

      if (def.getSynonyms() != null) {
        String synonyms = def.getSynonyms().parallelStream().collect(Collectors.joining(","));
        wordDefinition.setSynonyms(synonyms);
      }

      if (def.getExamples() != null) {
        Set<WordDefinitionExamples> examples = def
            .getExamples()
            .parallelStream()
            .map(ex -> WordDefinitionExamples.builder().example(ex).definition(wordDefinition).build())
            .collect(Collectors.toSet());
        wordDefinition.setExamples(examples);
      }
      return wordDefinition;
    }).collect(Collectors.toSet());
  }
}
