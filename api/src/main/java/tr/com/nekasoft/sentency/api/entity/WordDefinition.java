package tr.com.nekasoft.sentency.api.entity;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import tr.com.nekasoft.sentency.api.data.word.SynonymAntonym;
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

  @Column(name = "definition")
  private String definition;

  @Column(name = "definition_of")
  private String definitionOf;

  @Column(name = "definition_tr")
  private String definitionTr;

  @Column(name = "part_of_speech")
  private String partOfSpeech;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "word_id")
  private Word word;

  @Builder.Default
  @OneToMany(mappedBy = "definition", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private Set<WordDefinitionExamples> examples = new LinkedHashSet<>();

  @Builder.Default
  @OneToMany(mappedBy = "wordDefinition", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private Set<WordSynonymAntonym> synonymAntonym = new LinkedHashSet<>();

  public WordDefinitionResponse toResponse() {
    var builder = WordDefinitionResponse
        .builder()
        .id(id)
        .definitionTr(definitionTr)
        .definitionOf(definitionOf)
        .partOfSpeech(partOfSpeech)
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
        .definition(definition)
        .examples(examples.parallelStream().map(WordDefinitionExamples::getExample).collect(Collectors.toSet()));

    return builder.build();
  }
}
