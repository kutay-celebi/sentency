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

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "snt_word_definition_example")
public class WordDefinitionExamples extends BaseEntity {

  private static final long serialVersionUID = -3642757426340941961L;

  @Column(name = "example")
  private String example;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "definition_id")
  private WordDefinition definition;
}
