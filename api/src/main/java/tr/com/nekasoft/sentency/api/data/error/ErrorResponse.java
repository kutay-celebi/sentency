package tr.com.nekasoft.sentency.api.data.error;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
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
public class ErrorResponse implements Serializable {

  private static final long serialVersionUID = 386236682283135184L;

  private List<String> errors;
  @Builder.Default
  private Map<String, String> args = new HashMap<>();
  private String code;
  @Builder.Default
  private String uuid = UUID.randomUUID().toString();
}
