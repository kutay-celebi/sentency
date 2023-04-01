package tr.com.nekasoft.sentency.api.data.error;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import tr.com.nekasoft.sentency.api.exception.ExceptionCode;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ValidationErrorResponse implements Serializable {

  private static final long serialVersionUID = 8845635282842739877L;

  private List<ValidationErrorResponseItem> errors;
  @Builder.Default
  private String code = ExceptionCode.BAD_REQUEST.getCode();
  @Builder.Default
  private String uuid = UUID.randomUUID().toString();
}
