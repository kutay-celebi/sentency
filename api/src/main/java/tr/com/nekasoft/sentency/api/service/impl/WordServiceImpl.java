package tr.com.nekasoft.sentency.api.service.impl;

import java.util.Optional;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import tr.com.nekasoft.sentency.api.data.PageResponse;
import tr.com.nekasoft.sentency.api.data.word.WordFindRequest;
import tr.com.nekasoft.sentency.api.data.word.WordPageQueryRequest;
import tr.com.nekasoft.sentency.api.data.word.WordResponse;
import tr.com.nekasoft.sentency.api.entity.Word;
import tr.com.nekasoft.sentency.api.entity.WordDefinition;
import tr.com.nekasoft.sentency.api.exception.ExceptionCode;
import tr.com.nekasoft.sentency.api.external.google.GoogleTranslationExternalService;
import tr.com.nekasoft.sentency.api.external.wordsapi.GetWordsResponse;
import tr.com.nekasoft.sentency.api.external.wordsapi.WordsApiService;
import tr.com.nekasoft.sentency.api.repository.WordRepository;
import tr.com.nekasoft.sentency.api.service.WordService;

@Slf4j
@ApplicationScoped
public class WordServiceImpl implements WordService {

    @Inject
    @RestClient
    protected WordsApiService wordsApiService;

    @Inject
    protected WordRepository wordRepository;

    @Inject
    protected GoogleTranslationExternalService googleService;

    @Override
    public PageResponse<WordResponse> query(WordPageQueryRequest request) {
        return wordRepository.softPage(request).map(Word::toResponse);
    }

    @Override
    @Transactional
    public WordResponse getWord(String word) {
        Optional<Word> findWord =
            wordRepository.softFind(WordFindRequest.builder().word(word).build()).firstResultOptional();

        if (findWord.isPresent()) {
            return findWord.get().toResponse();
        }
        return fetchWordAndSave(word);
    }

    @Override
    public WordResponse findById(String id) {
        return wordRepository.softFindById(id).orElseThrow(ExceptionCode.DATA_NOT_FOUND::toException).toResponse();
    }

    private WordResponse fetchWordAndSave(String word) {

        Word toBeSaved = Word.builder().word(word).build();
        GetWordsResponse wordsApiResponse = wordsApiService.getWord(word);

        Set<WordDefinition> wordDefinitions = wordsApiResponse.toWordDefinitionEntity(toBeSaved);
        wordDefinitions.forEach(def -> {
            String translation = googleService.translate(def.getDefinition());
            def.setDefinitionTr(translation);
        });
        toBeSaved.setDefinitions(wordDefinitions);

        wordRepository.persistAndFlush(toBeSaved);
        return toBeSaved.toResponse();
    }

}
