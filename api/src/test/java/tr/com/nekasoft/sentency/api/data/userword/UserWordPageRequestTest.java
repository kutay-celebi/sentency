package tr.com.nekasoft.sentency.api.data.userword;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;

import io.quarkus.panache.common.Parameters;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import tr.com.nekasoft.sentency.api.data.StringQueryItem;

class UserWordPageRequestTest {

  @Nested
  class PrepareQuery {

    @Test
    void prepareQueryString() {
      // given
      StringQueryItem queryItem = StringQueryItem.builder().value("user").build();
      UserWordPageRequest instance = UserWordPageRequest.builder().userId(queryItem).build();

      // when
      String actual = instance.prepareQuery();

      // then
      assertThat(actual, equalTo("lower(user.id) = lower(:userId)"));

    }

    @Test
    void queryByWord() {
      // given
      StringQueryItem queryItem = StringQueryItem.builder().value("user").build();
      StringQueryItem queryItem2 = StringQueryItem.builder().value("word").build();
      UserWordPageRequest instance = UserWordPageRequest.builder().userId(queryItem).word(queryItem2).build();

      // when
      String actual = instance.prepareQuery();

      // then
      assertThat(actual, equalTo("lower(user.id) = lower(:userId) and lower(word.word) = lower(:word)"));

    }
  }

  @Nested
  class PrepareParameters {

    @Test
    void queryByWord() {
      // given
      StringQueryItem queryItem = StringQueryItem.builder().value("user").build();
      StringQueryItem queryItem2 = StringQueryItem.builder().value("word").build();
      UserWordPageRequest instance = UserWordPageRequest.builder().userId(queryItem).word(queryItem2).build();

      // when
      Parameters actual = instance.prepareParameters();

      // then
      assertThat(actual.map(), hasEntry("userId", "user"));
      assertThat(actual.map(), hasEntry("word", "word"));

    }
  }

}
