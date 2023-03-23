package tr.com.nekasoft.sentency.api.data.userword;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@Setter
public class UserWordResponse implements Serializable {
    private static final long serialVersionUID = -3632152286257249421L;

    private String id;
    private String userId;
    private String wordId;
    private String word;
    private Instant nextReview;
    private Instant lastReview;
    private Difficulty difficulty;
    private Long count;
}
