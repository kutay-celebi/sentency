package tr.com.nekasoft.sentency.api.data;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T> implements Serializable {

  private static final long serialVersionUID = -1189307328497331712L;

  private List<T> content;
  private int page;
  private int size;
  private int totalPage;

  public <U> PageResponse<U> map(Function<? super T, ? extends U> converter) {
    List<U> convertedContent = getConvertedContent(converter);
    return PageResponse.<U>builder()
        .content(convertedContent)
        .totalPage(totalPage)
        .page(page)
        .size(size)
        .build();
  }

  protected <U> List<U> getConvertedContent(Function<? super T, ? extends U> converter) {
    return content.stream().map(converter).collect(Collectors.toList());
  }
}
