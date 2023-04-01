package tr.com.nekasoft.sentency.api.service.impl;

import io.quarkus.panache.common.Parameters;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import tr.com.nekasoft.sentency.api.config.SentencyConfig;
import tr.com.nekasoft.sentency.api.data.DefaultQueryRequest;
import tr.com.nekasoft.sentency.api.data.PageResponse;
import tr.com.nekasoft.sentency.api.data.SortItem;
import tr.com.nekasoft.sentency.api.data.userword.UserWordDifficultyRequest;
import tr.com.nekasoft.sentency.api.data.userword.UserWordPageRequest;
import tr.com.nekasoft.sentency.api.data.userword.UserWordRequest;
import tr.com.nekasoft.sentency.api.data.userword.UserWordResponse;
import tr.com.nekasoft.sentency.api.entity.User;
import tr.com.nekasoft.sentency.api.entity.UserWord;
import tr.com.nekasoft.sentency.api.entity.Word;
import tr.com.nekasoft.sentency.api.exception.ExceptionCode;
import tr.com.nekasoft.sentency.api.repository.UserRepository;
import tr.com.nekasoft.sentency.api.repository.UserWordRepository;
import tr.com.nekasoft.sentency.api.repository.WordRepository;
import tr.com.nekasoft.sentency.api.service.UserWordService;

@ApplicationScoped
@RequiredArgsConstructor
public class UserWordServiceImpl implements UserWordService {

  private final SentencyConfig sentencyConfig;
  private final UserWordRepository userWordRepository;
  private final UserRepository userRepository;
  private final WordRepository wordRepository;

  @Transactional
  @Override
  public UserWordResponse addWord(UserWordRequest request) {
    DefaultQueryRequest userWordQuery = DefaultQueryRequest.builder()
        .query("word.id = :wordId")
        .parameters(
            Parameters.with("wordId", request.getWordId()))
        .build();

    Optional<UserWord> userWord = userWordRepository.softFind(userWordQuery).firstResultOptional();
    if (userWord.isEmpty()) {
      User user = userRepository.softFindById(request.getUserId())
          .orElseThrow(() -> ExceptionCode.DATA_NOT_FOUND.toException(
              Map.of("user-id", request.getUserId())));
      Word word = wordRepository.softFindById(request.getWordId())
          .orElseThrow(() -> ExceptionCode.DATA_NOT_FOUND.toException(
              Map.of("word-id", request.getWordId())));

      Duration toBeAdd = Duration.ofHours(sentencyConfig.review().medium().longValue());
      Instant initialNextReview = Instant.now().plus(toBeAdd);
      UserWord toBeSaved = UserWord.builder().nextReview(initialNextReview).user(user).word(word)
          .build();
      userWordRepository.persistAndFlush(toBeSaved);
      return toBeSaved.toResponse();
    }
    return userWord.get().toResponse();
  }

  @Override
  public UserWordResponse getNextReview(String userId) {
    var sort = SortItem.builder().field("nextReview").direction("asc").build();
    DefaultQueryRequest queryRequest = DefaultQueryRequest.builder()
        .query("user.id = :userId")
        .parameters(Parameters.with("userId", userId))
        .sorts(Collections.singletonList(sort))
        .build();
    return userWordRepository.softFind(queryRequest)
        .firstResultOptional()
        .orElseThrow(() -> ExceptionCode.NO_WORDS_ADDED.toException(Map.of("user-id", userId)))
        .toResponse();
  }

  @Transactional
  @Override
  public UserWordResponse adjustDifficulty(UserWordDifficultyRequest request) {
    UserWord userWord = userWordRepository.softFindById(request.getUserWordId())
        .orElseThrow(() -> ExceptionCode.DATA_NOT_FOUND.toException(
            Map.of("user-word-id", request.getUserWordId())));
    BigDecimal hoursToBeAdded;
    switch (request.getDifficulty()) {
      case EASY:
        hoursToBeAdded = sentencyConfig.review().easy();
        break;
      default:
      case MEDIUM:
        hoursToBeAdded = sentencyConfig.review().medium();
        break;
      case HARD:
        hoursToBeAdded = sentencyConfig.review().hard();
        break;
    }

    hoursToBeAdded = hoursToBeAdded.multiply(sentencyConfig.review().multiplier())
        .multiply(BigDecimal.valueOf(userWord.getCount()));

    userWord.setNextReview(
        userWord.getNextReview().plus(Duration.ofHours(hoursToBeAdded.longValue())));
    userWord.setDifficulty(request.getDifficulty());
    userWordRepository.persistAndFlush(userWord);
    return userWord.toResponse();
  }

  @Override
  public PageResponse<UserWordResponse> query(UserWordPageRequest request) {
    return userWordRepository.softPage(request).map(UserWord::toResponse);
  }

  @Override
  public UserWordResponse findById(String id) {
    return userWordRepository.softFindById(id)
        .orElseThrow(
            () -> ExceptionCode.DATA_NOT_FOUND.toException(Map.of("user-word-id", id)))
        .toResponse();
  }
}
