package tr.com.nekasoft.sentency.api.data.sentence;

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
import tr.com.nekasoft.sentency.api.data.PageQueryRequest;
import tr.com.nekasoft.sentency.api.data.SortItem;
import tr.com.nekasoft.sentency.api.data.StringQueryItem;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@Setter
public class SentencePageQueryRequest extends PageQueryRequest {

  private static final long serialVersionUID = -1739114757310096778L;

  @NotNull
  @Valid
  private StringQueryItem userId;
  @Valid
  private StringQueryItem wordId;
  @Valid
  private StringQueryItem word;
  @Builder.Default
  private List<SortItem> sorts = new ArrayList<>();

  @Override
  public String prepareQuery() {
    StringBuilder query = new StringBuilder(userId.toQuery("userWord.user.id", "userId"));
    if (word != null && !word.getValue().isBlank()) {
      query.append(AND).append(word.toQuery("userWord.word.word", "word"));
    }
    if (wordId != null && !wordId.getValue().isBlank()) {
      query.append(AND).append(wordId.toQuery("userWord.word.id", "wordId"));
    }

    return query.toString();
  }

  @Override
  public Parameters prepareParameters() {
    Parameters parameters = Parameters.with("userId", userId.parameterValue());
    if (word != null && !word.getValue().isBlank()) {
      parameters.and("word", word.parameterValue());
    }
    if (wordId != null && !wordId.getValue().isBlank()) {
      parameters.and("wordId", wordId.parameterValue());
    }
    return parameters;
  }

  @Override
  public Sort prepareSorts() {
    Sort sort = Sort.empty();
    sorts.forEach(entry -> {
      switch (entry.getField()) {
        case "word":
          sort.and("userWord.word.word", entry.toDirection());
          break;
      }
    });
    return sort;
  }
}
