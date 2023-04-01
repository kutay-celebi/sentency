package tr.com.nekasoft.sentency.api.data;

import io.quarkus.panache.common.Page;
import javax.ws.rs.QueryParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class PageQueryRequest implements QueryRequest {

  private static final long serialVersionUID = -3774188195262562044L;

  @QueryParam("size")
  @Builder.Default
  private int size = 15;
  @QueryParam("page")
  @Builder.Default
  private int page = 1;

  public Page toPage() {
    return Page.of(page - 1, size);
  }
}
