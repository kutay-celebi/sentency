package tr.com.nekasoft.sentency.api.data.userword;

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
public class UserWordDifficultyRequest implements Serializable {

  private static final long serialVersionUID = -5503335927336722596L;

  @NotNull
  private String userWordId;
  @NotNull
  private Difficulty difficulty;
}
