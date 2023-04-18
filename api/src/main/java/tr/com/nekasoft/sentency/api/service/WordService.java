package tr.com.nekasoft.sentency.api.service;

import tr.com.nekasoft.sentency.api.data.PageResponse;
import tr.com.nekasoft.sentency.api.data.word.WordDefinitionResponse;
import tr.com.nekasoft.sentency.api.data.word.WordPageQueryRequest;
import tr.com.nekasoft.sentency.api.data.word.WordResponse;

public interface WordService {

  WordDefinitionResponse voteDefinition(String definitionId);

  PageResponse<WordResponse> query(WordPageQueryRequest request);

  WordResponse getWord(String word);

  WordResponse findById(String id);
}
