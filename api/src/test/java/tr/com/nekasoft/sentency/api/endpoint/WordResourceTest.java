package tr.com.nekasoft.sentency.api.endpoint;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import tr.com.nekasoft.sentency.api.AbstractWordTestSuite;
import tr.com.nekasoft.sentency.api.entity.Word;

@QuarkusTest
class WordResourceTest extends AbstractWordTestSuite {

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

}
