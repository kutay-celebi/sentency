package tr.com.nekasoft.sentency.api.endpoint;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import tr.com.nekasoft.sentency.api.data.auth.LoginRequest;
import tr.com.nekasoft.sentency.api.data.auth.RegisterRequest;
import tr.com.nekasoft.sentency.api.external.google.GoogleOAuthExternal;
import tr.com.nekasoft.sentency.api.service.AuthService;

@Resource
@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

  @Inject
  AuthService authService;
  @Inject
  GoogleOAuthExternal googleOAuthExternal;

  @POST
  @Path("/register")
  public Response register(@Valid RegisterRequest request) {
    authService.register(request);
    return Response.ok().build();
  }

  @POST
  @Path("/login")
  @PermitAll
  public Response login(@Valid LoginRequest request) {
    return Response.ok(authService.login(request)).build();
  }

  @GET
  @Path("/login/google")
  @PermitAll
  public Response googleLogin(@QueryParam("auth-code") String token) {
    GoogleIdToken idToken = googleOAuthExternal.verifyToken(token);
    return Response.ok(authService.loginWithGoogle(idToken)).build();
  }

}
