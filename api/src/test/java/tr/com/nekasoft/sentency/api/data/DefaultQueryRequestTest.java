package tr.com.nekasoft.sentency.api.data;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;

import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import java.util.Collections;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class DefaultQueryRequestTest {

  @Nested
  class PrepareQuery {

    @Test
    void returnQueryString() {
      // given
      DefaultQueryRequest instance = DefaultQueryRequest.builder().query("query").build();

      // when
      String actual = instance.prepareQuery();

      // then
      assertThat(actual, equalTo("query"));
    }

    @Test
    void returnEmptyQueryIfQueryStringNull() {
      // given
      DefaultQueryRequest instance = DefaultQueryRequest.builder().build();

      // when
      String actual = instance.prepareQuery();

      // then
      assertThat(actual, equalTo(""));

    }

  }

  @Nested
  class PrepareParameters {

    @Test
    void returnParameters() {
      // given
      Parameters parameters = Parameters.with("dummy", "dummy");
      DefaultQueryRequest instance = DefaultQueryRequest.builder().parameters(parameters).build();

      // when
      Parameters actual = instance.prepareParameters();

      // then
      assertThat(actual, equalTo(parameters));

    }

    @Nested
    class PrepareSorts {

      @Test
      void returnSorts() {
        // given
        DefaultQueryRequest instance = DefaultQueryRequest.builder().build();

        // when
        Sort actual = instance.prepareSorts();

        // then
        assertThat(actual.getColumns(), empty());

      }

      @Test
      void addColumnsToSort() {
        // given
        DefaultQueryRequest instance = DefaultQueryRequest.builder()
            .sorts(Collections.singletonList(SortItem.builder().field("field").build()))
            .build();

        // when
        Sort actual = instance.prepareSorts();

        // then
        assertThat(actual.getColumns().get(0).getName(), equalTo("field"));
      }

      @Test
      void sortItemListIsNull() {
        // given
        DefaultQueryRequest instance = DefaultQueryRequest.builder().sorts(null).build();

        // when
        Sort actual = instance.prepareSorts();

        // then
        assertThat(actual.getColumns(), empty());

      }
    }
  }
}
