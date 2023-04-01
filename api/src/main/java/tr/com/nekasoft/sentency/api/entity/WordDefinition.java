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

  @Column(name = "definition_tr")
  private String definitionTr;

  @Column(name = "part_of_speech")
  private String partOfSpeech;

  @Column(name = "synonyms")
  private String synonyms;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "word_id")
  private Word word;

  @Builder.Default
  @OneToMany(mappedBy = "definition", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private Set<WordDefinitionExamples> examples = new LinkedHashSet<>();

  public WordDefinitionResponse toResponse() {
    var builder = WordDefinitionResponse.builder()
        .id(id)
        .definitionTr(definitionTr)
        .partOfSpeech(partOfSpeech)
        .definition(definition)
        .examples(examples.parallelStream()
            .map(WordDefinitionExamples::getExample)
            .collect(Collectors.toSet()));

    if (synonyms != null) {
      builder.synonyms(Set.of(synonyms.split(",")));
    }

    return builder.build();
  }
}
