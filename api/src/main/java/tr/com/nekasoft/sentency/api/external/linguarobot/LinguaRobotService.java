package tr.com.nekasoft.sentency.api.external.linguarobot;

import io.quarkus.rest.client.reactive.ClientExceptionMapper;
import java.lang.reflect.Method;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.reactive.RestPath;
import tr.com.nekasoft.sentency.api.exception.ExceptionCode;

@RegisterRestClient(configKey = "lingua-robot")
public interface LinguaRobotService {

  @GET
  @Path("language/v1/entries/en/{word}")
  LrResponse getWord(@RestPath("word") String word);

  @ClientExceptionMapper
  static RuntimeException toException(Response response, Method method) {
    if (response.getStatus() == 404) {
      return ExceptionCode.DATA_NOT_FOUND.toException();
    }
    return null;
  }
}
