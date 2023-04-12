package tr.com.nekasoft.sentency.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import tr.com.nekasoft.sentency.api.data.word.WordDefinitionPhrasesResponse;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "snt_word_definition_phrases")
public class WordDefinitionPhrases extends BaseEntity {

  private static final long serialVersionUID = -191651141323314441L;

  @Column(name = "lang")
  private String lang;

  @Column(name = "part_of_speech")
  private String partOfSpeech;

  @Column(name = "definition")
  private String definition;

  @Column(name = "definition_of")
  private String definitionOf;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "word_definition_id")
  private WordDefinition wordDefinition;

  public WordDefinitionPhrasesResponse toResponse() {
    return WordDefinitionPhrasesResponse
        .builder()
        .definition(definition)
        .definitionOf(definitionOf)
        .partOfSpeech(partOfSpeech)
        .build();
  }
}
