package tr.com.nekasoft.sentency.api.endpoint;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import com.github.javafaker.Faker;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.ValidatableResponse;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import tr.com.nekasoft.sentency.api.data.auth.RegisterRequest;
import tr.com.nekasoft.sentency.api.entity.User;
import tr.com.nekasoft.sentency.api.exception.ExceptionCode;
import tr.com.nekasoft.sentency.api.repository.UserRepository;

@QuarkusTest
class AuthResourceTest {

  @Inject
  protected UserRepository userRepository;

  @BeforeEach
  void setUp() {

  }

  @Nested
  @TestHTTPEndpoint(AuthResource.class)
  public class Register {

    @Test
    void shouldRegister() {
      // given
      RegisterRequest payload = RegisterRequest.builder()
          .username(Faker.instance().internet().emailAddress())
          .password(RandomStringUtils.randomAlphanumeric(10))
          .build();

      // when
      ValidatableResponse actual = given().contentType(MediaType.APPLICATION_JSON)
          .body(payload)
          .post("/register")
          .then()
          .log()
          .all();

      // then
      actual.statusCode(200);

    }

    @Test
    void usernameShouldBeUnique() {
      // given
      String username = Faker.instance().internet().emailAddress();
      String password = RandomStringUtils.randomAlphanumeric(10);
      RegisterRequest payload = RegisterRequest.builder().username(username).password(password)
          .build();
      userRepository.persistAndFlush(User.builder().username(username).password(password).build());

      // when
      ValidatableResponse actual = given().contentType(MediaType.APPLICATION_JSON)
          .body(payload)
          .post("/register")
          .then()
          .log()
          .all();

      // then
      actual.statusCode(ExceptionCode.USER_USERNAME_EXIST.getStatus().getStatusCode());
      actual.body("code", equalTo(ExceptionCode.USER_USERNAME_EXIST.getCode()));

    }
  }

}
