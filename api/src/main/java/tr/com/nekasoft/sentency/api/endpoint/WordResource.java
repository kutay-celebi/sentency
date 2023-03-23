package tr.com.nekasoft.sentency.api.endpoint;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import tr.com.nekasoft.sentency.api.data.word.WordPageQueryRequest;
import tr.com.nekasoft.sentency.api.service.WordService;

@Resource
@Produces(APPLICATION_JSON)
@Path("/word")
@Slf4j
public class WordResource {

    @Inject
    WordService wordService;

    @GET
    @Path("/{word}")
    public Response getWord(@PathParam("word") String word) {
        return Response.ok(wordService.getWord(word)).build();
    }

    @POST
    @Path("/query")
    @Consumes(APPLICATION_JSON)
    public Response query(@RequestBody WordPageQueryRequest request) {
        return Response.ok(wordService.query(request)).build();
    }

    @GET
    @Path("/id/{id}")
    public Response findById(@PathParam("id") String id) {
        return Response.ok(wordService.findById(id)).build();
    }
}
