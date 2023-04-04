package tr.com.nekasoft.sentency.api.external.google;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import tr.com.nekasoft.sentency.api.exception.ExceptionCode;

@ApplicationScoped
public class GoogleOAuthExternal {

  @ConfigProperty(name = "google.client-id")
  protected String googleClientId;

  public GoogleIdToken verifyToken(final String token) {
    try {
      List<String> clientIds = Collections.singletonList(googleClientId);
      var verifier = new GoogleIdTokenVerifier.Builder(GoogleNetHttpTransport.newTrustedTransport(),
          GsonFactory.getDefaultInstance()).setAudience(clientIds).build();
      return verifier.verify(token);
    } catch (GeneralSecurityException | IOException e) {
      throw ExceptionCode.EXTERNAL_SERVICE.toException();
    }
  }

}
