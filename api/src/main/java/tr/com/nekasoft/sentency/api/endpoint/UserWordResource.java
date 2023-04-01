package tr.com.nekasoft.sentency.api.endpoint;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import tr.com.nekasoft.sentency.api.data.userword.UserWordDifficultyRequest;
import tr.com.nekasoft.sentency.api.data.userword.UserWordPageRequest;
import tr.com.nekasoft.sentency.api.data.userword.UserWordRequest;
import tr.com.nekasoft.sentency.api.service.UserWordService;

@Resource
@Path("/user-word")
@Produces(APPLICATION_JSON)
public class UserWordResource {

  @Inject
  UserWordService userWordService;

  @GET
  @Path("/{user-id}/next-review")
  public Response nextReview(@PathParam("user-id") String userId) {
    return Response.ok(userWordService.getNextReview(userId)).build();
  }

  @GET
  @Path("/{id}")
  public Response findById(@PathParam("id") String id) {
    return Response.ok(userWordService.findById(id)).build();
  }

  @POST
  @Consumes(APPLICATION_JSON)
  @Path("/query")
  public Response query(@Valid UserWordPageRequest request) {
    return Response.ok(userWordService.query(request)).build();
  }

  @PUT
  @Consumes(APPLICATION_JSON)
  @Path("/difficulty")
  public Response difficulty(@Valid UserWordDifficultyRequest request) {
    return Response.ok(userWordService.adjustDifficulty(request)).build();
  }

  @POST
  @Consumes(APPLICATION_JSON)
  public Response addWord(@Valid UserWordRequest request) {
    return Response.ok(userWordService.addWord(request)).build();
  }
}
