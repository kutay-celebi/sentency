package tr.com.nekasoft.sentency.api.endpoint;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import io.quarkus.cache.CacheInvalidate;
import io.quarkus.cache.CacheResult;
import io.quarkus.security.Authenticated;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import tr.com.nekasoft.sentency.api.data.word.WordPageQueryRequest;
import tr.com.nekasoft.sentency.api.service.WordService;

@Slf4j
@Authenticated
@Resource
@Path("/word")
@Produces(APPLICATION_JSON)
public class WordResource {

  @Inject
  WordService wordService;

  @GET
  @CacheResult(cacheName = "search-word")
  @Path("/{word}")
  public Response getWord(@PathParam("word") String word) {
    return Response.ok(wordService.getWord(word.trim().toLowerCase())).build();
  }

  @PUT
  @CacheInvalidate(cacheName = "search-word")
  @Path("/vote/{definition-id}")
  public Response voteDefinition(@PathParam("definition-id") String definitionId) {
    return Response.ok(wordService.voteDefinition(definitionId)).build();
  }

  @POST
  @Path("/query")
  @Consumes(APPLICATION_JSON)
  public Response query(@RequestBody WordPageQueryRequest request) {
    return Response.ok(wordService.query(request)).build();
  }

  @GET
  @Path("/id/{id}")
  @CacheResult(cacheName = "word-by-id")
  public Response findById(@PathParam("id") String id) {
    return Response.ok(wordService.findById(id)).build();
  }
}
