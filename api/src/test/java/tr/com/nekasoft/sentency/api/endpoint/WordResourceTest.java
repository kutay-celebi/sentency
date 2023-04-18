package tr.com.nekasoft.sentency.api.endpoint;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import io.quarkus.cache.CacheManager;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.quarkus.test.security.TestSecurity;
import io.restassured.response.ValidatableResponse;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import tr.com.nekasoft.sentency.api.AbstractWordTestSuite;
import tr.com.nekasoft.sentency.api.entity.Word;
import tr.com.nekasoft.sentency.api.exception.ExceptionCode;
import tr.com.nekasoft.sentency.api.external.google.GoogleTranslationExternalService;
import tr.com.nekasoft.sentency.api.external.linguarobot.LinguaRobotService;
import tr.com.nekasoft.sentency.api.external.linguarobot.LrEntry;
import tr.com.nekasoft.sentency.api.external.linguarobot.LrLexemes;
import tr.com.nekasoft.sentency.api.external.linguarobot.LrResponse;
import tr.com.nekasoft.sentency.api.external.linguarobot.LrSense;
import tr.com.nekasoft.sentency.api.external.linguarobot.LrSynonymAntonym;

@QuarkusTest
class WordResourceTest extends AbstractWordTestSuite {

  @InjectMock
  @RestClient
  LinguaRobotService linguaRobotService;

  @InjectMock
  GoogleTranslationExternalService googleTranslationExternalService;

  @Inject
  CacheManager cacheManager;

  @Nested
  @TestHTTPEndpoint(WordResource.class)
  @TestSecurity(authorizationEnabled = false)
  class FindById {

    @Test
    void success() {
      // given
      Word expected = saveWord();

      // when
      ValidatableResponse actual = given().when().get("/id/{id}", expected.getId()).then().log().all();

      // then
      actual.statusCode(200);
      actual.body("id", equalTo(expected.getId()));

    }
  }

  @Nested
  @TestHTTPEndpoint(WordResource.class)
  @TestSecurity(authorizationEnabled = false)
  class SearchWord {

    @Test
    void success() {
      // given
      String word = RandomStringUtils.randomAlphanumeric(5);
      mockLrService(word);
      when(googleTranslationExternalService.translate(anyString())).thenReturn("translated");

      // when
      ValidatableResponse actual = given().when().get("/{word}", word).then().log().all();

      // then
      actual.statusCode(200);
      actual.body("word", equalTo(word.toLowerCase().trim()));
      actual.body("definitions.phrases.en.definition", contains(word));
      actual.body("definitions.phrases.en.partOfSpeech", contains("noun"));
      actual.body("definitions.phrases.tr.definition", contains("translated"));

      actual.body("definitions.synonyms.word.flatten()", contains("synonym"));
      actual.body("definitions.synonyms.definition.flatten()", contains("synonym-sense"));

      actual.body("definitions.antonyms.word.flatten()", contains("antonym"));
      actual.body("definitions.antonyms.definition.flatten()", contains("antonym-sense"));

      actual.body("definitions.examples.flatten()", contains("example"));

    }

    @Test
    void wordNotFound() {
      // given
      when(linguaRobotService.getWord(anyString())).thenReturn(
          LrResponse.builder().entries(Collections.emptyList()).build());

      // when
      ValidatableResponse actual = given()
          .when()
          .get("/{word}", RandomStringUtils.randomAlphanumeric(5))
          .then()
          .log()
          .all();

      // then
      actual.statusCode(404);
      actual.body("code", equalTo(ExceptionCode.DATA_NOT_FOUND.getCode()));

    }
  }

  @Nested
  @TestHTTPEndpoint(WordResource.class)
  @TestSecurity(authorizationEnabled = false)
  class VoteDefinition {

    @Test
    void success() {
      // given
      Word word = saveWord();

      // when
      ValidatableResponse actual = given()
          .when()
          .put("/vote/{definition-id}", word.getDefinitions().iterator().next().getId())
          .then()
          .log()
          .all();

      // then
      actual.statusCode(200);

    }

    @Test
    void unknownWodDefinition() {
      // given
      // when
      ValidatableResponse actual = given().when().put("/vote/{definition-id}", "unknown").then().log().all();

      // then
      actual.statusCode(404);
      actual.body("code", equalTo(ExceptionCode.DATA_NOT_FOUND.getCode()));

    }
  }

  @Nested
  @TestHTTPEndpoint(WordResource.class)
  class Security {

    @Test
    void searchWord() {
      // given
      // when
      ValidatableResponse actual = given().when().get("/{word}", "test2").then().log().all();

      // then
      actual.statusCode(HttpStatus.SC_UNAUTHORIZED);

    }

    @Test
    void success() {
      // given
      // when
      ValidatableResponse actual = given().when().get("/id/{id}", "dummy").then().log().all();

      // then
      actual.statusCode(HttpStatus.SC_UNAUTHORIZED);

    }

  }


  @Nested
  @TestHTTPEndpoint(WordResource.class)
  @TestSecurity(authorizationEnabled = false)
  class Cache {

    @Test
    void searchWord() {
      // given
      String word = RandomStringUtils.randomAlphanumeric(5);
      mockLrService(word);

      // when
      ValidatableResponse actual = given().when().get("/{word}", word).then().log().all();

      // then
      actual.statusCode(200);
      Collection<String> caches = cacheManager.getCacheNames();
      MatcherAssert.assertThat(caches, not(empty()));

    }
  }

  private void mockLrService(String word) {
    LrSynonymAntonym synonymAntonym = LrSynonymAntonym
        .builder()
        .sense("synonym-sense")
        .synonyms(List.of("synonym"))
        .build();
    LrSynonymAntonym synonymAntonym1 = LrSynonymAntonym
        .builder()
        .sense("antonym-sense")
        .antonyms(List.of("antonym"))
        .build();
    LrSense sense = LrSense.builder().definition(word).usageExamples(Collections.singletonList("example")).build();
    LrLexemes lexeme = LrLexemes
        .builder()
        .lemma(word)
        .partOfSpeech("noun")
        .synonymSets(Collections.singleton(synonymAntonym))
        .antonymSets(Collections.singleton(synonymAntonym1))
        .senses(Collections.singletonList(sense))
        .build();
    LrEntry entry = LrEntry.builder().entry(word).lexemes(Collections.singletonList(lexeme)).build();
    LrResponse response = LrResponse.builder().entries(Collections.singletonList(entry)).build();
    when(linguaRobotService.getWord(word.toLowerCase().trim())).thenReturn(response);
  }

}
