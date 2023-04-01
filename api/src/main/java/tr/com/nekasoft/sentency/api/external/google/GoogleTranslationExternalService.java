package tr.com.nekasoft.sentency.api.external.google;

import com.google.cloud.translate.v3.LocationName;
import com.google.cloud.translate.v3.TranslateTextRequest;
import com.google.cloud.translate.v3.TranslationServiceClient;
import java.io.IOException;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Slf4j
@ApplicationScoped
public class GoogleTranslationExternalService {

  @ConfigProperty(name = "google.project-id")
  protected String googleProjectId;

  public String translate(String text) {
    try (TranslationServiceClient client = TranslationServiceClient.create()) {
      LocationName parent = LocationName.of(googleProjectId, "global");
      TranslateTextRequest translateTextRequest = TranslateTextRequest.newBuilder()
          .setMimeType(MediaType.TEXT_PLAIN)
          .setTargetLanguageCode("tr")
          .setParent(parent.toString())
          .addContents(text)
          .build();
      return client.translateText(translateTextRequest).getTranslationsList().get(0)
          .getTranslatedText();
    } catch (IOException e) {
      log.error("google exception", e);
      return null;
    }

  }
}
