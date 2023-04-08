package tr.com.nekasoft.sentency.api.endpoint;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import io.quarkus.cache.CacheManager;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.quarkus.test.security.TestSecurity;
import io.restassured.response.ValidatableResponse;
import java.util.Collection;
import java.util.Collections;
import javax.inject.Inject;
import org.apache.http.HttpStatus;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import tr.com.nekasoft.sentency.api.AbstractWordTestSuite;
import tr.com.nekasoft.sentency.api.entity.Word;
import tr.com.nekasoft.sentency.api.external.google.GoogleTranslationExternalService;
import tr.com.nekasoft.sentency.api.external.wordsapi.Definition;
import tr.com.nekasoft.sentency.api.external.wordsapi.GetWordsResponse;
import tr.com.nekasoft.sentency.api.external.wordsapi.WordsApiService;

@QuarkusTest
class WordResourceTest extends AbstractWordTestSuite {

  @InjectMock
  @RestClient
  WordsApiService wordsApiService;

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
      Definition definition = Definition
          .builder()
          .definition("definition")
          .synonyms(Collections.singletonList("synonyms"))
          .examples(Collections.singletonList("example"))
          .build();
      GetWordsResponse response = GetWordsResponse.builder().results(Collections.singletonList(definition)).build();
      when(wordsApiService.getWord("test")).thenReturn(response);

      when(googleTranslationExternalService.translate(any())).thenReturn("translated");

      // when
      ValidatableResponse actual = given().when().get("/{word}", "test").then().log().all();

      // then
      actual.statusCode(200);
      actual.body("word", equalTo("test"));
      actual.body("definitions.definition", contains("definition"));
      actual.body("definitions.definitionTr", contains("translated"));
      actual.body("definitions[0].synonyms", contains("synonyms"));
      actual.body("definitions[0].examples", contains("example"));

    }

    @Test
    void resultsArrayIsNull() {
      // given
      GetWordsResponse response = GetWordsResponse.builder().build();
      when(wordsApiService.getWord("test2")).thenReturn(response);

      // when
      ValidatableResponse actual = given().when().get("/{word}", "test2").then().log().all();

      // then
      actual.statusCode(404);

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
      Definition definition = Definition
          .builder()
          .definition("definition")
          .synonyms(Collections.singletonList("synonyms"))
          .examples(Collections.singletonList("example"))
          .build();
      GetWordsResponse response = GetWordsResponse.builder().results(Collections.singletonList(definition)).build();
      when(wordsApiService.getWord("test")).thenReturn(response);

      when(googleTranslationExternalService.translate(any())).thenReturn("translated");

      // when
      ValidatableResponse actual = given().when().get("/{word}", "test").then().log().all();

      // then
      actual.statusCode(200);
      Collection<String> caches = cacheManager.getCacheNames();
      MatcherAssert.assertThat(caches, hasSize(1));

    }

  }

}
