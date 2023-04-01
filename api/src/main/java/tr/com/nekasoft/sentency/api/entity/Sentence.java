package tr.com.nekasoft.sentency.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import tr.com.nekasoft.sentency.api.data.sentence.SentenceResponse;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "snt_sentence")
public class Sentence extends BaseEntity {

  private static final long serialVersionUID = -8413799128537856385L;

  @NotEmpty
  @Column(name = "sentence")
  private String sentence;

  @Column(name = "sentence_tr")
  private String sentenceTr;

  @ManyToOne
  @JoinColumn(name = "user_word_id")
  private UserWord userWord;

  public SentenceResponse toPageResponse() {
    return SentenceResponse.builder()
        .id(id)
        .sentence(sentence)
        .wordId(userWord.getWord().getId())
        .word(userWord.getWord().getWord())
        .difficulty(userWord.getDifficulty())
        .nextReview(userWord.getNextReview())
        .lastReview(userWord.getLastReview())
        .build();
  }
}
