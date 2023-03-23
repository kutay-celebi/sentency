package tr.com.nekasoft.sentency.api.data.sentence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SentenceTranslateResponse {

    private String sentence;
    private String translation;
}
