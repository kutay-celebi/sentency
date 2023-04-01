package tr.com.nekasoft.sentency.api.service;

import tr.com.nekasoft.sentency.api.data.PageResponse;
import tr.com.nekasoft.sentency.api.data.userword.UserWordDifficultyRequest;
import tr.com.nekasoft.sentency.api.data.userword.UserWordPageRequest;
import tr.com.nekasoft.sentency.api.data.userword.UserWordRequest;
import tr.com.nekasoft.sentency.api.data.userword.UserWordResponse;

public interface UserWordService {

  UserWordResponse addWord(UserWordRequest request);

  UserWordResponse getNextReview(String userId);

  UserWordResponse adjustDifficulty(UserWordDifficultyRequest request);

  PageResponse<UserWordResponse> query(UserWordPageRequest request);

  UserWordResponse findById(String id);

  UserWordResponse removeReviewList(String userWordId);
}
