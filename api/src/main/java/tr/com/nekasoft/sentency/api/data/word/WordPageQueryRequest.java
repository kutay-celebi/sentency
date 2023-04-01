package tr.com.nekasoft.sentency.api.data.word;

import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import tr.com.nekasoft.sentency.api.data.PageQueryRequest;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class WordPageQueryRequest extends PageQueryRequest {

  private static final long serialVersionUID = 5409684864156839119L;

  private String word;

  @Override
  public String prepareQuery() {
    StringBuilder query = new StringBuilder();
    if (word != null) {
      query.append("lower(word) like lower(:word)");
    }
    return query.toString();
  }

  @Override
  public Parameters prepareParameters() {
    Parameters parameters = new Parameters();
    if (word != null) {
      parameters.and("word", "%" + word + "%");
    }
    return parameters;
  }

  @Override
  public Sort prepareSorts() {
    return Sort.empty();
  }
}
