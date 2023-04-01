package tr.com.nekasoft.sentency.api.entity;

import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import tr.com.nekasoft.sentency.api.data.userword.Difficulty;
import tr.com.nekasoft.sentency.api.data.userword.UserWordResponse;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "snt_user_word")
public class UserWord extends BaseEntity {

  private static final long serialVersionUID = -8587053723615150632L;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "word_id")
  private Word word;

  @Builder.Default
  @Column(name = "difficulty")
  @Enumerated(EnumType.STRING)
  private Difficulty difficulty = Difficulty.MEDIUM;

  @NotNull
  @Column(name = "next_review")
  private Instant nextReview;

  @Column(name = "last_review")
  private Instant lastReview;

  @Builder.Default
  @Column(name = "review_count")
  private Long count = 0L;

  @Builder.Default
  @Column(name = "is_active")
  private Boolean isActive = Boolean.TRUE;

  public UserWordResponse toResponse() {
    return UserWordResponse.builder()
        .id(id)
        .word(word.getWord())
        .userId(user.getId())
        .wordId(word.getId())
        .nextReview(nextReview)
        .lastReview(lastReview)
        .difficulty(difficulty)
        .count(count)
        .isActive(isActive)
        .build();
  }
}
