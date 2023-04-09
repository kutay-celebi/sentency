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
public class LrSense implements Serializable {

  private static final long serialVersionUID = -4100856741785406271L;

  private String definition;
  private List<String> usageExamples;
  private List<LrSense> subSenses;
}
