package tr.com.nekasoft.sentency.api.endpoint;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import javax.inject.Inject;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import tr.com.nekasoft.sentency.api.AbstractWordTestSuite;
import tr.com.nekasoft.sentency.api.data.StringQueryItem;
import tr.com.nekasoft.sentency.api.data.sentence.SentencePageQueryRequest;
import tr.com.nekasoft.sentency.api.data.sentence.SentenceRequest;
import tr.com.nekasoft.sentency.api.entity.Sentence;
import tr.com.nekasoft.sentency.api.entity.User;
import tr.com.nekasoft.sentency.api.entity.UserWord;
import tr.com.nekasoft.sentency.api.entity.Word;
import tr.com.nekasoft.sentency.api.exception.ExceptionCode;
import tr.com.nekasoft.sentency.api.external.google.GoogleTranslationExternalService;
import tr.com.nekasoft.sentency.api.repository.SentenceRepository;

@QuarkusTest
class SentenceResourceTest extends AbstractWordTestSuite {

    @InjectMock
    GoogleTranslationExternalService translationExternalService;
    @Inject
    SentenceRepository sentenceRepository;

    @TestHTTPEndpoint(SentenceResource.class)
    @Nested
    public class Translate {

        @Test
        public void translateSentence() {
            // given
            Mockito.when(translationExternalService.translate(Mockito.any())).thenReturn("translated");

            // when
            ValidatableResponse actual =
                given().queryParam("sentence", "sentence").when().get("/translate").then().log().all();

            // then
            actual.statusCode(200);
            actual.body("sentence", equalTo("sentence"));
            actual.body("translation", equalTo("translated"));

        }
    }

    @Nested
    @TestHTTPEndpoint(SentenceResource.class)
    public class Save {

        @Test
        void unknownParameters() {
            // given
            SentenceRequest payload =
                SentenceRequest.builder().userId("unknown").wordId("word-id").sentence("sentence").build();

            // when
            ValidatableResponse actual =
                given().contentType(ContentType.JSON).when().body(payload).post().then().log().all();

            // then
            actual.statusCode(404);
            actual.body("code", equalTo(ExceptionCode.DATA_NOT_FOUND.getCode()));
            actual.body("args.word-id", equalTo("word-id"));
            actual.body("args.user-id", equalTo("unknown"));

        }

        @Test
        public void success() {
            // given
            User user = saveUser();
            Word word = saveWord();
            UserWord userWord = saveUserWord(user, word);

            SentenceRequest payload =
                SentenceRequest.builder().sentence("sentence").userId(user.getId()).wordId(word.getId()).build();

            // when
            ValidatableResponse actual = given().contentType(ContentType.JSON).body(payload).post().then().log().all();

            // then
            actual.statusCode(200);
            actual.body("id", notNullValue());
            actual.body("wordId", equalTo(word.getId()));
            actual.body("userId", equalTo(user.getId()));
            actual.body("userWordId", equalTo(userWord.getId()));

        }
    }

    @Nested
    @TestHTTPEndpoint(SentenceResource.class)
    public class Query {

        @Test
        public void success() {
            // given
            User user = saveUser();
            Word word = saveWord();
            UserWord userWord = saveUserWord(user, word);
            Sentence toBeSaved = Sentence.builder().sentence("sentence").userWord(userWord).build();
            sentenceRepository.persistAndFlush(toBeSaved);

            SentencePageQueryRequest payload = SentencePageQueryRequest.builder()
                                                                       .userId(StringQueryItem.builder()
                                                                                              .value(user.getId())
                                                                                              .build())
                                                                       .build();

            // when
            ValidatableResponse actual =
                given().contentType(ContentType.JSON).body(payload).post("/query").then().log().all();

            // then
            actual.statusCode(200);
            actual.body("content.sentence", contains(toBeSaved.getSentence()));
            actual.body("content.id", contains(toBeSaved.getId()));
            actual.body("content.wordId", contains(word.getId()));
            actual.body("content.word", contains(word.getWord()));
            actual.body("content.difficulty", contains(userWord.getDifficulty().name()));
            actual.body("content.nextReview", notNullValue());
        }
    }

}
