package tr.com.nekasoft.sentency.api.data.word;

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
public class WordResponse implements Serializable {

  private static final long serialVersionUID = -7260994087974803646L;

  private String id;
  private String word;
  private List<WordDefinitionResponse> definitions;
}
