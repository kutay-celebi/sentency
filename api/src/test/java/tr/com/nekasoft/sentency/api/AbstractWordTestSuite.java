package tr.com.nekasoft.sentency.api;

import java.time.Instant;
import javax.inject.Inject;
import org.apache.commons.lang3.RandomStringUtils;
import tr.com.nekasoft.sentency.api.entity.User;
import tr.com.nekasoft.sentency.api.entity.UserWord;
import tr.com.nekasoft.sentency.api.entity.Word;
import tr.com.nekasoft.sentency.api.repository.UserWordRepository;
import tr.com.nekasoft.sentency.api.repository.WordRepository;

public abstract class AbstractWordTestSuite extends AbstractUserTestSuite {

  @Inject
  protected WordRepository wordRepository;
  @Inject
  protected UserWordRepository userWordRepository;

  protected Word saveWord() {
    Word word = Word.builder().word(RandomStringUtils.randomAlphanumeric(10)).build();
    wordRepository.persistAndFlush(word);
    return word;
  }

  protected UserWord saveUserWord(User user, Word word) {
    return saveUserWord(user, word, true);
  }

  protected UserWord saveUserWord(User user, Word word, Boolean isActive) {
    UserWord toBeSaved = UserWord.builder().word(word).user(user).isActive(isActive).nextReview(Instant.now()).build();
    userWordRepository.persistAndFlush(toBeSaved);
    return toBeSaved;
  }
}
