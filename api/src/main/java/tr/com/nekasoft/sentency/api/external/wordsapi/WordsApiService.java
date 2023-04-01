package tr.com.nekasoft.sentency.api.external.wordsapi;

import io.quarkus.rest.client.reactive.ClientExceptionMapper;
import java.lang.reflect.Method;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import tr.com.nekasoft.sentency.api.exception.ExceptionCode;

@RegisterRestClient(configKey = "words-api")
public interface WordsApiService {

  @GET
  @Path("/words/{definition}")
  GetWordsResponse getWord(@PathParam("definition") String definition);

  @ClientExceptionMapper
  static RuntimeException toException(Response response, Method method) {
    if (response.getStatus() == 404) {
      return ExceptionCode.DATA_NOT_FOUND.toException();
    }
    return null;
  }
}
