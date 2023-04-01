package tr.com.nekasoft.sentency.api.entity;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import tr.com.nekasoft.sentency.api.data.word.WordResponse;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "snt_word")
public class Word extends BaseEntity {

  private static final long serialVersionUID = -4583374991974774015L;

  private String word;

  @Builder.Default
  @OneToMany(mappedBy = "word", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private Set<WordDefinition> definitions = new LinkedHashSet<>();

  public WordResponse toResponse() {
    return WordResponse.builder()
        .id(id)
        .word(word)
        .definitions(
            definitions.stream().map(WordDefinition::toResponse).collect(Collectors.toList()))
        .build();
  }
}
