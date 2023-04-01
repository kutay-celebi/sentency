package tr.com.nekasoft.sentency.api.data.sentence;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
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
public class SentenceRequest implements Serializable {

  private static final long serialVersionUID = 7850384795439213590L;
  @NotNull
  private String wordId;
  @NotNull
  private String userId;
  @NotNull
  private String sentence;
  private String sentenceTr;
}
