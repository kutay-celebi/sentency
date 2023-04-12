package tr.com.nekasoft.sentency.api.data.word;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WordDefinitionPhrasesResponse implements Serializable {

  private static final long serialVersionUID = -4805586882160614874L;

  private String definition;
  private String definitionOf;
  private String partOfSpeech;
}
