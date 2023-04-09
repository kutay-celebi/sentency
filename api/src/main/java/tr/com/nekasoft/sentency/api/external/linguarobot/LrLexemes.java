package tr.com.nekasoft.sentency.api.external.linguarobot;

import java.io.Serializable;
import java.util.List;
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
public class LrLexemes implements Serializable {

  private static final long serialVersionUID = -5428736736027131304L;

  private String lemma;
  private String partOfSpeech;
  private List<LrSense> senses;
  private Set<LrSynonymAntonym> synonymSets;
  private Set<LrSynonymAntonym> antonymSets;
}
