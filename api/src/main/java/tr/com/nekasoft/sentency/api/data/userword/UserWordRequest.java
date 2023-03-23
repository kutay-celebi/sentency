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
public class UserWordRequest implements Serializable {
    private static final long serialVersionUID = 7110248089133475080L;

    @NotNull
    private String wordId;
    @NotNull
    private String userId;
    @Builder.Default
    private Difficulty difficulty = Difficulty.MEDIUM;
}
