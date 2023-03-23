package tr.com.nekasoft.sentency.api.data;

import java.util.ArrayList;
import java.util.List;

import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@Setter
public class DefaultQueryRequest implements QueryRequest {
    private static final long serialVersionUID = -6115833132318509289L;

    private String query;
    @Builder.Default
    private Parameters parameters = new Parameters();

    @Builder.Default
    private List<SortItem> sorts = new ArrayList<>();

    @Override
    public String prepareQuery() {
        if (query != null) {
            return query;
        }
        return "";
    }

    @Override
    public Parameters prepareParameters() {
        return parameters;
    }

    @Override
    public Sort prepareSorts() {
        Sort sort = Sort.empty();
        sorts.forEach(entry -> sort.and(entry.getField(), entry.toDirection()));
        return sort;
    }
}
