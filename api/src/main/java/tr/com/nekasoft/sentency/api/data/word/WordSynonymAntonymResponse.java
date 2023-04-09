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
public class WordSynonymAntonymResponse implements Serializable {

  private static final long serialVersionUID = 2900524732772541409L;

  private String definition;
  private String word;
}
