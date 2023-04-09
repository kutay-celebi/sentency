package tr.com.nekasoft.sentency.api.external.linguarobot;

import java.io.Serializable;
import java.util.List;
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
public class LrSynonymAntonym implements Serializable {

  private static final long serialVersionUID = -5005104290170476476L;

  private String sense;
  private List<String> synonyms;
  private List<String> antonyms;
}
