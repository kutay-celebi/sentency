package tr.com.nekasoft.sentency.api.data.userword;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

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
