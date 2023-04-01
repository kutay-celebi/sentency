package tr.com.nekasoft.sentency.api.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Parameters;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import tr.com.nekasoft.sentency.api.data.PageQueryRequest;
import tr.com.nekasoft.sentency.api.data.PageResponse;
import tr.com.nekasoft.sentency.api.data.QueryRequest;
import tr.com.nekasoft.sentency.api.entity.BaseEntity;

public interface BaseRepository<T extends BaseEntity> extends PanacheRepositoryBase<T, String> {

  String DELETED_EXPRESSION = "deleted = :deleted";
  String ID_EXPRESSION = "id = :id";
  String AND = " and ";

  default void softDeleteById(String s) {
    T item = findById(s);
    item.setDeleted(true);
    item.setDeletedAt(Instant.now());
    persist(item);
  }

  default Optional<T> softFindById(String s) {
    return find(ID_EXPRESSION + AND + DELETED_EXPRESSION,
        Parameters.with("id", s).and("deleted", false)).firstResultOptional();
  }

  default PanacheQuery<T> softFind(QueryRequest request) {
    StringBuilder query = new StringBuilder(DELETED_EXPRESSION);
    if (request.prepareQuery() != null && !request.prepareQuery().isBlank()) {
      query.append(AND).append(request.prepareQuery());
    }
    return find(query.toString(), request.prepareSorts(),
        request.prepareParameters().and("deleted", false));
  }

  default PanacheQuery<T> softFind() {
    return find(DELETED_EXPRESSION, Parameters.with("deleted", false));
  }

  default PageResponse<T> softPage(PageQueryRequest request) {
    StringBuilder query = new StringBuilder(DELETED_EXPRESSION);
    if (request.prepareQuery() != null && !request.prepareQuery().isBlank()) {
      query.append(AND);
      query.append(request.prepareQuery());
    }
    Page page = request.toPage();
    PanacheQuery<T> pageQuery = find(query.toString(),
        request.prepareSorts(),
        request.prepareParameters().and("deleted", false)).page(page);
    List<T> list = pageQuery.list();
    int count = pageQuery.pageCount();
    return PageResponse.<T>builder().content(list).size(page.size).page(page.index + 1)
        .totalPage(count).build();
  }

}
