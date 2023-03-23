package tr.com.nekasoft.sentency.api.service;

import javax.validation.Valid;

import tr.com.nekasoft.sentency.api.data.PageResponse;
import tr.com.nekasoft.sentency.api.data.sentence.SentencePageQueryRequest;
import tr.com.nekasoft.sentency.api.data.sentence.SentencePersistResponse;
import tr.com.nekasoft.sentency.api.data.sentence.SentenceRequest;
import tr.com.nekasoft.sentency.api.data.sentence.SentenceResponse;
import tr.com.nekasoft.sentency.api.data.sentence.SentenceTranslateResponse;

public interface SentenceService {
    SentencePersistResponse save(@Valid SentenceRequest request);

    SentenceTranslateResponse translate(String sentence);

    PageResponse<SentenceResponse> query(SentencePageQueryRequest request);
}
