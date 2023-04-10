package tr.com.nekasoft.sentency.api.endpoint;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.github.javafaker.Faker;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import io.quarkus.panache.common.Parameters;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.response.ValidatableResponse;
import java.util.Optional;
import javax.ws.rs.core.MediaType;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.sshd.common.config.keys.loader.openssh.kdf.BCrypt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import tr.com.nekasoft.sentency.api.AbstractUserTestSuite;
import tr.com.nekasoft.sentency.api.data.DefaultQueryRequest;
import tr.com.nekasoft.sentency.api.data.auth.LoginRequest;
import tr.com.nekasoft.sentency.api.data.auth.RegisterRequest;
import tr.com.nekasoft.sentency.api.entity.User;
import tr.com.nekasoft.sentency.api.exception.ExceptionCode;
import tr.com.nekasoft.sentency.api.external.google.GoogleOAuthExternal;

@QuarkusTest
class AuthResourceTest extends AbstractUserTestSuite {

  @InjectMock
  GoogleOAuthExternal googleOAuthExternal;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Nested
  @TestHTTPEndpoint(AuthResource.class)
  public class Register {

    @Test
    void shouldRegister() {
      // given
      RegisterRequest payload = RegisterRequest
          .builder()
          .username(Faker.instance().internet().emailAddress())
          .password(RandomStringUtils.randomAlphanumeric(10))
          .build();

      // when
      ValidatableResponse actual = given()
          .contentType(MediaType.APPLICATION_JSON)
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
      RegisterRequest payload = RegisterRequest.builder().username(username).password(password).build();
      userRepository.persistAndFlush(User.builder().username(username).password(password).build());

      // when
      ValidatableResponse actual = given()
          .contentType(MediaType.APPLICATION_JSON)
          .body(payload)
          .post("/register")
          .then()
          .log()
          .all();

      // then
      actual.statusCode(ExceptionCode.USER_USERNAME_EXIST.getStatus().getStatusCode());
      actual.body("code", equalTo(ExceptionCode.USER_USERNAME_EXIST.getCode()));

    }

    @Test
    void shouldInitializeUserConfig() {
      // given
      RegisterRequest payload = RegisterRequest
          .builder()
          .username(Faker.instance().internet().emailAddress())
          .password(RandomStringUtils.randomAlphanumeric(10))
          .build();

      // when
      ValidatableResponse actual = given()
          .contentType(MediaType.APPLICATION_JSON)
          .body(payload)
          .post("/register")
          .then()
          .log()
          .all();

      // then
      actual.statusCode(200);

      Optional<User> actualData = userRepository
          .softFind(DefaultQueryRequest
              .builder()
              .query("username = :username")
              .parameters(Parameters.with("username", payload.getUsername()))
              .build())
          .firstResultOptional();
      assertThat(actualData.isPresent(), equalTo(true));
      assertThat(actualData.get().getUserConfig(), notNullValue());
    }
  }


  @Nested
  @TestHTTPEndpoint(AuthResource.class)
  public class Login {

    @Test
    void loginSuccess() {
      // given
      User user = saveUser();
      LoginRequest payload = LoginRequest.builder().username(user.getUsername()).password("password").build();

      // when
      ValidatableResponse actual = given()
          .contentType(MediaType.APPLICATION_JSON)
          .body(payload)
          .post("/login")
          .then()
          .log()
          .all();

      // then
      actual.statusCode(200);
      actual.body("token", notNullValue());
      actual.body("targetLanguage", equalTo("tr"));

    }

    @Test
    void wrongPassword() {
      // given
      User user = saveUser();
      LoginRequest payload = LoginRequest.builder().username(user.getPassword()).password("wrong").build();

      // when
      ValidatableResponse actual = given()
          .contentType(MediaType.APPLICATION_JSON)
          .body(payload)
          .post("/login")
          .then()
          .log()
          .all();

      // then
      actual.statusCode(ExceptionCode.BAD_CREDENTIAL.getStatus().getStatusCode());
      actual.body("code", equalTo(ExceptionCode.BAD_CREDENTIAL.getCode()));

    }

    @Test
    void unregisteredUser() {
      // given
      LoginRequest payload = LoginRequest.builder().username("wronguser").password("password").build();

      // when
      ValidatableResponse actual = given()
          .contentType(MediaType.APPLICATION_JSON)
          .body(payload)
          .post("/login")
          .then()
          .log()
          .all();

      // then
      actual.statusCode(ExceptionCode.BAD_CREDENTIAL.getStatus().getStatusCode());
      actual.body("code", equalTo(ExceptionCode.BAD_CREDENTIAL.getCode()));

    }

    @Test
    void userConfigIsNotExisting() {
      // given
      String password = BCrypt.hashpw("password", BCrypt.gensalt(12));
      User user = User.builder().username(Faker.instance().internet().emailAddress()).password(password).build();

      userRepository.persistAndFlush(user);
      LoginRequest payload = LoginRequest.builder().username(user.getUsername()).password("password").build();

      // when
      ValidatableResponse actual = given()
          .contentType(MediaType.APPLICATION_JSON)
          .body(payload)
          .post("/login")
          .then()
          .log()
          .all();

      // then
      actual.statusCode(200);
      actual.body("token", notNullValue());
      actual.body("targetLanguage", equalTo("tr"));
    }
  }


  @Nested
  @TestHTTPEndpoint(AuthResource.class)
  public class LoginWithGoogle {

    @Test
    void loginSuccess() {
      // given
      String idToken = "id-token";
      GoogleIdToken mockIdToken = mock(GoogleIdToken.class);
      Payload payload = new Payload();
      payload.setEmail(Faker.instance().internet().emailAddress());

      when(mockIdToken.getPayload()).thenReturn(payload);
      when(googleOAuthExternal.verifyToken(idToken)).thenReturn(mockIdToken);

      // when
      ValidatableResponse actual = given().queryParam("auth-code", idToken).get("/login/google").then().log().all();

      // then
      actual.statusCode(200);
      actual.body("token", notNullValue());
      actual.body("targetLanguage", equalTo("tr"));
    }
  }
}
