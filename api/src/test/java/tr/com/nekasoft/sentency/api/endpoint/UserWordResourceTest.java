package tr.com.nekasoft.sentency.api.endpoint;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static tr.com.nekasoft.sentency.api.data.userword.Difficulty.HARD;

import java.time.Duration;
import java.time.Instant;

import javax.inject.Inject;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import tr.com.nekasoft.sentency.api.AbstractWordTestSuite;
import tr.com.nekasoft.sentency.api.config.SentencyConfig;
import tr.com.nekasoft.sentency.api.data.StringQueryItem;
import tr.com.nekasoft.sentency.api.data.userword.UserWordDifficultyRequest;
import tr.com.nekasoft.sentency.api.data.userword.UserWordPageRequest;
import tr.com.nekasoft.sentency.api.data.userword.UserWordRequest;
import tr.com.nekasoft.sentency.api.data.userword.UserWordResponse;
import tr.com.nekasoft.sentency.api.entity.User;
import tr.com.nekasoft.sentency.api.entity.UserWord;
import tr.com.nekasoft.sentency.api.entity.Word;
import tr.com.nekasoft.sentency.api.exception.ExceptionCode;
import tr.com.nekasoft.sentency.api.repository.UserWordRepository;

@QuarkusTest
public class UserWordResourceTest extends AbstractWordTestSuite {

    @Inject
    protected UserWordRepository userWordRepository;
    @Inject
    protected SentencyConfig sentencyConfig;

    @Nested
    @TestHTTPEndpoint(UserWordResource.class)
    class NextReview {

        @Test
        void unknownUser() {
            // given

            // when
            ValidatableResponse actual = given().when().get("/{user-id}/next-review", "unknown").then().log().all();

            // then
            actual.statusCode(404);
            actual.body("code", equalTo(ExceptionCode.DATA_NOT_FOUND.getCode()));
            actual.body("args.user-id", equalTo("unknown"));

        }

        @Test
        void returnSortedByNextReview() {
            // given
            User user = saveUser();
            Word word = saveWord();
            Instant now = Instant.now();
            UserWord expected = UserWord.builder().word(word).user(user).nextReview(now).build();
            userWordRepository.persistAndFlush(expected);
            Word word2 = saveWord();
            userWordRepository.persistAndFlush(UserWord.builder()
                                                       .word(word2)
                                                       .user(user)
                                                       .nextReview(now.plusMillis(3000))
                                                       .build());

            // when
            ValidatableResponse actual = given().when().get("/{user-id}/next-review", user.getId()).then().log().all();

            // then
            actual.statusCode(200);
            actual.body("id", equalTo(expected.getId()));
        }
    }

    @Nested
    @TestHTTPEndpoint(UserWordResource.class)
    class FindById {

        @Test
        void unknownUser() {
            // given

            // when
            ValidatableResponse actual = given().when().get("/{id}", "unknown").then().log().all();

            // then
            actual.statusCode(404);
            actual.body("code", equalTo(ExceptionCode.DATA_NOT_FOUND.getCode()));
            actual.body("args.user-word-id", equalTo("unknown"));

        }

        @Test
        void returnSortedByNextReview() {
            // given
            User user = saveUser();
            Word word = saveWord();
            Instant now = Instant.now();
            UserWord expected = UserWord.builder().word(word).user(user).nextReview(now).build();
            userWordRepository.persistAndFlush(expected);
            Word word2 = saveWord();
            userWordRepository.persistAndFlush(UserWord.builder()
                                                       .word(word2)
                                                       .user(user)
                                                       .nextReview(now.plusMillis(3000))
                                                       .build());

            // when
            ValidatableResponse actual = given().when().get("/{id}", expected.getId()).then().log().all();

            // then
            actual.statusCode(200);
            actual.body("id", equalTo(expected.getId()));
        }
    }

    @Nested
    @TestHTTPEndpoint(UserWordResource.class)
    class AddWord {

        @Test
        void unknownUser() {
            // given
            Word word = saveWord();
            UserWordRequest payload = UserWordRequest.builder().userId("unknown").wordId(word.getId()).build();

            // when
            ValidatableResponse actual = given().contentType(ContentType.JSON).body(payload).post().then().log().all();

            // then
            actual.statusCode(404);
            actual.body("code", equalTo(ExceptionCode.DATA_NOT_FOUND.getCode()));
            actual.body("args.user-id", equalTo("unknown"));

        }

        @Test
        void unknownWord() {
            // given
            User user = saveUser();
            UserWordRequest payload = UserWordRequest.builder().userId(user.getId()).wordId("unknown").build();

            // when
            ValidatableResponse actual = given().contentType(ContentType.JSON).body(payload).post().then().log().all();

            // then
            actual.statusCode(404);
            actual.body("code", equalTo(ExceptionCode.DATA_NOT_FOUND.getCode()));
            actual.body("args.word-id", equalTo("unknown"));

        }

        @Test
        void ifExistsThenReturnExistingId() {
            // given
            User user = saveUser();
            Word word = saveWord();
            UserWord existing = UserWord.builder().word(word).user(user).nextReview(Instant.now()).build();
            userWordRepository.persistAndFlush(existing);

            UserWordRequest payload = UserWordRequest.builder().userId(user.getId()).wordId(word.getId()).build();

            // when
            ValidatableResponse actual = given().contentType(ContentType.JSON).body(payload).post().then().log().all();

            // then
            actual.statusCode(200);
            actual.body("id", equalTo(existing.getId()));
            actual.body("userId", equalTo(user.getId()));
            actual.body("wordId", equalTo(word.getId()));

        }

        @Test
        void success() {
            // given
            User user = saveUser();
            Word word = saveWord();
            UserWordRequest payload = UserWordRequest.builder().userId(user.getId()).wordId(word.getId()).build();

            // when
            ValidatableResponse actual = given().contentType(ContentType.JSON).body(payload).post().then().log().all();

            // then
            actual.statusCode(200);
            actual.body("id", notNullValue());
            actual.body("userId", equalTo(user.getId()));
            actual.body("wordId", equalTo(word.getId()));

        }

        @Test
        void nextReviewShouldBeInitialized() {
            // given
            User user = saveUser();
            Word word = saveWord();
            UserWordRequest payload = UserWordRequest.builder().userId(user.getId()).wordId(word.getId()).build();
            Instant expectedNextReview =
                Instant.now().plus(Duration.ofHours(sentencyConfig.review().medium().longValue()));

            // when
            ValidatableResponse actual = given().contentType(ContentType.JSON).body(payload).post().then().log().all();

            // then

            actual.statusCode(200);
            UserWordResponse actualBody = actual.extract().as(UserWordResponse.class);
            MatcherAssert.assertThat(actualBody.getNextReview(), greaterThanOrEqualTo(expectedNextReview));

        }

    }

    @Nested
    @TestHTTPEndpoint(UserWordResource.class)
    class GetUserWordList {

        @Test
        void success() {
            User user = saveUser();
            Word word = saveWord();
            Instant now = Instant.now();
            UserWord userWord = UserWord.builder().word(word).user(user).nextReview(now).build();
            userWordRepository.persistAndFlush(userWord);

            // given
            UserWordPageRequest payload =
                UserWordPageRequest.builder().userId(StringQueryItem.builder().value(user.getId()).build()).build();

            // when
            ValidatableResponse actual =
                given().contentType(ContentType.JSON).body(payload).post("/query").then().log().all();

            // then
            actual.statusCode(200);
            actual.body("content.userId", contains(user.getId()));
            actual.body("content.wordId", contains(word.getId()));
            actual.body("content.id", contains(userWord.getId()));
            actual.body("content.word", contains(word.getWord()));

        }

    }

    @Nested
    @TestHTTPEndpoint(UserWordResource.class)
    class AdjustDifficulty {
        @Test
        void adjustDifficulty() {
            // given
            User user = saveUser();
            Word word = saveWord();
            UserWord userWord = saveUserWord(user, word);

            UserWordDifficultyRequest payload =
                UserWordDifficultyRequest.builder().difficulty(HARD).userWordId(userWord.getId()).build();

            // when
            ValidatableResponse actual =
                given().contentType(ContentType.JSON).body(payload).put("/difficulty").then().log().all();

            // then
            actual.statusCode(200);
            actual.body("id", equalTo(userWord.getId()));
            actual.body("difficulty", equalTo(HARD.name()));
        }

        @Test
        void nextReviewShouldBeAdjusted() {
            // given
            User user = saveUser();
            Word word = saveWord();
            UserWord userWord = saveUserWord(user, word);

            UserWordDifficultyRequest payload =
                UserWordDifficultyRequest.builder().difficulty(HARD).userWordId(userWord.getId()).build();

            // when
            ValidatableResponse actual =
                given().contentType(ContentType.JSON).body(payload).put("/difficulty").then().log().all();

            // then
            actual.statusCode(200);
            actual.body("nextReview", not(equalTo(userWord.getNextReview().toString())));

        }

        @Test
        void unknownId() {
            // given

            UserWordDifficultyRequest payload =
                UserWordDifficultyRequest.builder().difficulty(HARD).userWordId("unknown").build();

            // when
            ValidatableResponse actual =
                given().contentType(ContentType.JSON).body(payload).put("/difficulty").then().log().all();

            // then
            actual.statusCode(404);
            actual.body("code", equalTo(ExceptionCode.DATA_NOT_FOUND.getCode()));
            actual.body("args.user-word-id", equalTo("unknown"));

        }
    }

}
