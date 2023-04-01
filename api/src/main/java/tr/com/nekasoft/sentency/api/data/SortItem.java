package tr.com.nekasoft.sentency.api.data;

import io.quarkus.panache.common.Sort;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@Setter
public class SortItem implements Serializable {

  private static final long serialVersionUID = 5800322904636440873L;

  private String field;
  @Builder.Default
  private String direction = "asc";

  public Sort.Direction toDirection() {
    switch (direction.toLowerCase()) {
      case "asc":
        return Sort.Direction.Ascending;
      case "desc":
        return Sort.Direction.Descending;
    }
    return Sort.Direction.Ascending;
  }
}
