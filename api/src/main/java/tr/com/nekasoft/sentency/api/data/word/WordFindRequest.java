package tr.com.nekasoft.sentency.api.data.word;

import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import tr.com.nekasoft.sentency.api.data.QueryRequest;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class WordFindRequest implements QueryRequest {
    private static final long serialVersionUID = 6706762938809640350L;

    private String word;

    @Override
    public String prepareQuery() {
        return "word = :word";
    }

    @Override
    public Parameters prepareParameters() {
        return Parameters.with("word", word);
    }

    @Override
    public Sort prepareSorts() {
        return Sort.empty();
    }
}
