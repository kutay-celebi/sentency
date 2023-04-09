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
public class LrEntry implements Serializable {

  private static final long serialVersionUID = 4372918949376739483L;

  private String entry;
  private List<LrLexemes> lexemes;
}
