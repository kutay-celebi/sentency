package tr.com.nekasoft.sentency.api.data.auth;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
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
public class LoginRequest implements Serializable {

  private static final long serialVersionUID = -6879887782315079878L;

  @NotNull
  private String username;
  @NotNull
  private String password;
}
