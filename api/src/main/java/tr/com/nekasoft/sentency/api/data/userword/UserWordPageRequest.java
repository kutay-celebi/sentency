package tr.com.nekasoft.sentency.api.data.userword;

import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import tr.com.nekasoft.sentency.api.data.PageQueryRequest;
import tr.com.nekasoft.sentency.api.data.SortItem;
import tr.com.nekasoft.sentency.api.data.StringQueryItem;

@AllArgsConstructor
@SuperBuilder
@Getter
@NoArgsConstructor
@Setter
public class UserWordPageRequest extends PageQueryRequest {

  private static final long serialVersionUID = -1605568343095134220L;

  @NotNull
  @Valid
  private StringQueryItem userId;
  @Valid
  private StringQueryItem word;

  @Builder.Default
  private List<SortItem> sorts = new ArrayList<>();

  @Override
  public String prepareQuery() {
    StringBuilder query = new StringBuilder(userId.toQuery("user.id", "userId"));
    if (word != null && !word.getValue().isBlank()) {
      query.append(AND).append(word.toQuery("word.word", "word"));
    }
    return query.toString();
  }

  @Override
  public Parameters prepareParameters() {
    Parameters parameters = Parameters.with("userId", userId.parameterValue());
    if (word != null && !word.getValue().isBlank()) {
      parameters.and("word", word.parameterValue());
    }
    return parameters;
  }

  @Override
  public Sort prepareSorts() {
    Sort sort = Sort.empty();
    sorts.forEach(entry -> {
      switch (entry.getField()) {
        case "word":
          sort.and("word.word", entry.toDirection());
          break;
        case "nextReview":
          sort.and("nextReview", entry.toDirection());
          break;
      }
    });
    return sort;
  }
}
