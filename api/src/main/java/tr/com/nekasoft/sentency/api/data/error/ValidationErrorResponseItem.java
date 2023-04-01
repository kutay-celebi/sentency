package tr.com.nekasoft.sentency.api.data.error;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ValidationErrorResponseItem implements Serializable {

  private static final long serialVersionUID = -8288342935891488302L;

  private String path;
  private String message;
  private String value;

}
