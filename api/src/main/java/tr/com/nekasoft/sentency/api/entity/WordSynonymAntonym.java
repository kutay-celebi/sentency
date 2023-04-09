package tr.com.nekasoft.sentency.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import tr.com.nekasoft.sentency.api.data.word.SynonymAntonym;
import tr.com.nekasoft.sentency.api.data.word.WordSynonymAntonymResponse;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "snt_word_synonym_antonym")
public class WordSynonymAntonym extends BaseEntity {

  private static final long serialVersionUID = -2974697784254480246L;

  @Column(name = "definition")
  private String definition;
  @Column(name = "word")
  private String word;
  @Enumerated(EnumType.STRING)
  @Column(name = "type")
  private SynonymAntonym type;

  @ManyToOne
  @JoinColumn(name = "word_definition_id")
  private WordDefinition wordDefinition;

  public WordSynonymAntonymResponse toResponse() {
    return WordSynonymAntonymResponse.builder().definition(definition).word(word).build();
  }

}
