package tr.com.nekasoft.sentency.api.data.sentence;

import java.io.Serializable;

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
public class SentencePersistResponse implements Serializable {
    private static final long serialVersionUID = -3493908013862958109L;

    private String id;
    private String wordId;
    private String userId;
    private String userWordId;

}
