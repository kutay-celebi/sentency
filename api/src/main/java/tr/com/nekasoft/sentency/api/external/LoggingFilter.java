// package tr.com.nekasoft.sentency.api.external;
//
// import lombok.extern.slf4j.Slf4j;
// import org.jboss.resteasy.reactive.client.impl.ClientRequestContextImpl;
// import org.jboss.resteasy.reactive.client.impl.RestClientRequestContext;
//
// import javax.ws.rs.client.ClientRequestContext;
// import javax.ws.rs.client.ClientResponseContext;
// import javax.ws.rs.client.ClientResponseFilter;
// import java.io.IOException;
//
// @Slf4j
// public class LoggingFilter implements ClientResponseFilter {
//     @Override
//     public void filter(ClientRequestContext requestContext, ClientResponseContext responseContext) throws IOException {
//         RestClientRequestContext restClientContext = ((ClientRequestContextImpl) requestContext).getRestClientRequestContext();
//         //        ResponseImpl response = ClientResponseCompleteRestHandler.mapToResponse(restClientContext, false);
//         //        log.info(response.readEntity(LinkedHashMap.class).toString());
//
//         log.info("{} - {}, Status: {}", restClientContext.getHttpMethod(), restClientContext.getUri().getPath(),
//                  restClientContext.getResponseStatus());
//     }
// }
