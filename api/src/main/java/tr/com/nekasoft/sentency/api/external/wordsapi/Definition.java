package tr.com.nekasoft.sentency.api.external.wordsapi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Definition implements Serializable {
    private static final long serialVersionUID = -7433120788356718259L;

    private String definition;
    private String definitionTr;
    private String partOfSpeech;
    private List<String> synonyms;
    private List<String> typeOf;
    private List<String> derivation;
    private List<String> examples;

}
