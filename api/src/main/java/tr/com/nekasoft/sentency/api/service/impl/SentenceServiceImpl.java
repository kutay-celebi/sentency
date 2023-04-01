package tr.com.nekasoft.sentency.api.service.impl;

import io.quarkus.panache.common.Parameters;
import java.time.Instant;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tr.com.nekasoft.sentency.api.config.SentencyConfig;
import tr.com.nekasoft.sentency.api.data.DefaultQueryRequest;
import tr.com.nekasoft.sentency.api.data.PageResponse;
import tr.com.nekasoft.sentency.api.data.sentence.SentencePageQueryRequest;
import tr.com.nekasoft.sentency.api.data.sentence.SentencePersistResponse;
import tr.com.nekasoft.sentency.api.data.sentence.SentenceRequest;
import tr.com.nekasoft.sentency.api.data.sentence.SentenceResponse;
import tr.com.nekasoft.sentency.api.data.sentence.SentenceTranslateResponse;
import tr.com.nekasoft.sentency.api.entity.Sentence;
import tr.com.nekasoft.sentency.api.entity.UserWord;
import tr.com.nekasoft.sentency.api.exception.ExceptionCode;
import tr.com.nekasoft.sentency.api.external.google.GoogleTranslationExternalService;
import tr.com.nekasoft.sentency.api.repository.SentenceRepository;
import tr.com.nekasoft.sentency.api.repository.UserWordRepository;
import tr.com.nekasoft.sentency.api.service.SentenceService;

@ApplicationScoped
@RequiredArgsConstructor
@Slf4j
public class SentenceServiceImpl implements SentenceService {

  private final UserWordRepository userWordRepository;
  private final SentenceRepository sentenceRepository;
  private final GoogleTranslationExternalService googleService;
  private final SentencyConfig sentencyConfig;

  @Transactional
  @Override
  public SentencePersistResponse save(@Valid SentenceRequest request) {

    DefaultQueryRequest query = DefaultQueryRequest.builder()
        .query("user.id = :userId and word.id = :wordId")
        .parameters(Parameters.with("userId", request.getUserId())
            .and("wordId", request.getWordId()))
        .build();
    UserWord userWord = userWordRepository.softFind(query)
        .firstResultOptional()
        .orElseThrow(() -> ExceptionCode.DATA_NOT_FOUND.toException(Map.of(
            "user-id",
            request.getUserId(),
            "word-id",
            request.getWordId())));
    Sentence toBeSaved = Sentence.builder()
        .userWord(userWord)
        .sentence(request.getSentence())
        .sentenceTr(request.getSentenceTr())
        .build();
    userWord.setCount(userWord.getCount() + 1);
    userWord.setLastReview(Instant.now());

    sentenceRepository.persistAndFlush(toBeSaved);
    userWordRepository.persistAndFlush(userWord);

    return SentencePersistResponse.builder()
        .id(toBeSaved.getId())
        .wordId(userWord.getWord().getId())
        .userWordId(userWord.getId())
        .userId(userWord.getUser().getId())
        .build();

  }

  @Override
  public SentenceTranslateResponse translate(String sentence) {
    String translate = googleService.translate(sentence);
    return SentenceTranslateResponse.builder().sentence(sentence).translation(translate).build();
  }

  @Override
  public PageResponse<SentenceResponse> query(SentencePageQueryRequest request) {
    return sentenceRepository.softPage(request).map(Sentence::toPageResponse);
  }
}
