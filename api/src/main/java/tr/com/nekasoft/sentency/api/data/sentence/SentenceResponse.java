package tr.com.nekasoft.sentency.api.data.sentence;

import java.io.Serializable;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tr.com.nekasoft.sentency.api.data.userword.Difficulty;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@Setter
public class SentenceResponse implements Serializable {

  private static final long serialVersionUID = -1767575181877182583L;

  private String id;
  private String sentence;

  private String wordId;
  private String word;
  private Difficulty difficulty;

  private Instant nextReview;
  private Instant lastReview;
}
