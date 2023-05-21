package tr.com.nekasoft.sentency.api.endpoint;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import io.quarkus.security.Authenticated;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestResponse;
import tr.com.nekasoft.sentency.api.data.PageResponse;
import tr.com.nekasoft.sentency.api.data.userword.UserWordDifficultyRequest;
import tr.com.nekasoft.sentency.api.data.userword.UserWordPageRequest;
import tr.com.nekasoft.sentency.api.data.userword.UserWordRequest;
import tr.com.nekasoft.sentency.api.data.userword.UserWordResponse;
import tr.com.nekasoft.sentency.api.service.UserWordService;

@Resource
@Authenticated
@Path("/user-word")
@Produces(APPLICATION_JSON)
public class UserWordResource {

  @Inject
  UserWordService userWordService;

  @GET
  @Path("/{user-id}/next-review")
  public RestResponse<UserWordResponse> nextReview(@PathParam("user-id") String userId) {
    return RestResponse.ok(userWordService.getNextReview(userId));
  }

  @GET
  @Path("/{id}")
  public RestResponse<UserWordResponse> findById(@PathParam("id") String id) {
    return RestResponse.ok(userWordService.findById(id));
  }

  @POST
  @Consumes(APPLICATION_JSON)
  @Path("/query")
  public RestResponse<PageResponse<UserWordResponse>> query(@Valid UserWordPageRequest request) {
    return RestResponse.ok(userWordService.query(request));
  }

  @PUT
  @Consumes(APPLICATION_JSON)
  @Path("/difficulty")
  public RestResponse<UserWordResponse> difficulty(@Valid UserWordDifficultyRequest request) {
    return RestResponse.ok(userWordService.adjustDifficulty(request));
  }

  @POST
  @Consumes(APPLICATION_JSON)
  public RestResponse<UserWordResponse> addWord(@Valid UserWordRequest request) {
    return RestResponse.ok(userWordService.addWord(request));
  }

  @DELETE
  @Consumes(APPLICATION_JSON)
  @Path("/{user-word-id}")
  public RestResponse<UserWordResponse> removeWord(@RestPath("user-word-id") String userWordId) {
    return RestResponse.ok(userWordService.removeReviewList(userWordId));
  }
}
