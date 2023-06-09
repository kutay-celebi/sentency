package tr.com.nekasoft.sentency.api.data.word;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
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
public class WordDefinitionResponse implements Serializable {

  private static final long serialVersionUID = -5358304466295154777L;

  private String id;
  private Set<String> examples;
  private Set<WordSynonymAntonymResponse> synonyms;
  private Set<WordSynonymAntonymResponse> antonyms;
  @Builder.Default
  private Map<String, WordDefinitionPhrasesResponse> phrases = new HashMap<>();
}
