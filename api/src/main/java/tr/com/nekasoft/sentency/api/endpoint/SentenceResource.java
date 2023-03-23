package tr.com.nekasoft.sentency.api.endpoint;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import tr.com.nekasoft.sentency.api.data.sentence.SentencePageQueryRequest;
import tr.com.nekasoft.sentency.api.data.sentence.SentenceRequest;
import tr.com.nekasoft.sentency.api.service.SentenceService;

@Resource
@Path("/sentence")
@Produces(APPLICATION_JSON)
public class SentenceResource {

    @Inject
    SentenceService sentenceService;

    @POST
    @Consumes(APPLICATION_JSON)
    public Response save(@Valid SentenceRequest request) {
        return Response.ok(sentenceService.save(request)).build();
    }

    @GET
    @Path("/translate")
    public Response translate(@QueryParam("sentence") String sentence) {
        return Response.ok(sentenceService.translate(sentence)).build();
    }

    @POST
    @Path("/query")
    @Consumes(APPLICATION_JSON)
    public Response query(@Valid SentencePageQueryRequest request) {
        return Response.ok(sentenceService.query(request)).build();
    }
}
