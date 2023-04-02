package tr.com.nekasoft.sentency.api.endpoint;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.response.ValidatableResponse;
import java.util.Collections;
import org.eclipse.microprofile.rest.client.inject.RestClient;
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

  @Nested
  @TestHTTPEndpoint(WordResource.class)
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


}
