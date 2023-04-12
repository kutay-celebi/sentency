package tr.com.nekasoft.sentency.api.entity;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import tr.com.nekasoft.sentency.api.data.word.SynonymAntonym;
import tr.com.nekasoft.sentency.api.data.word.WordDefinitionPhrasesResponse;
import tr.com.nekasoft.sentency.api.data.word.WordDefinitionResponse;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "snt_word_definition")
public class WordDefinition extends BaseEntity {

  private static final long serialVersionUID = -3642757426340941961L;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "word_id")
  private Word word;

  @Builder.Default
  @OneToMany(mappedBy = "definition", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private Set<WordDefinitionExamples> examples = new LinkedHashSet<>();

  @Builder.Default
  @OneToMany(mappedBy = "wordDefinition", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private Set<WordSynonymAntonym> synonymAntonym = new LinkedHashSet<>();

  @Builder.Default
  @MapKey(name = "lang")
  @OneToMany(mappedBy = "wordDefinition", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private Map<String, WordDefinitionPhrases> phrases = new HashMap<>();

  public WordDefinitionResponse toResponse() {
    var builder = WordDefinitionResponse
        .builder()
        .id(id)
        .synonyms(synonymAntonym
            .stream()
            .filter(synonymAntonym -> SynonymAntonym.SYNONYM.equals(synonymAntonym.getType()))
            .map(WordSynonymAntonym::toResponse)
            .collect(Collectors.toSet()))
        .antonyms(synonymAntonym
            .stream()
            .filter(synonymAntonym -> SynonymAntonym.ANTONYM.equals(synonymAntonym.getType()))
            .map(WordSynonymAntonym::toResponse)
            .collect(Collectors.toSet()))
        .examples(examples.parallelStream().map(WordDefinitionExamples::getExample).collect(Collectors.toSet()));

    Map<String, WordDefinitionPhrasesResponse> phrases = this.phrases
        .values()
        .stream()
        .collect(Collectors.toMap(WordDefinitionPhrases::getLang, WordDefinitionPhrases::toResponse));

    builder.phrases(phrases);

    return builder.build();
  }
}
