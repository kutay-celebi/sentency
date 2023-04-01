package tr.com.nekasoft.sentency.api.data;

import javax.validation.constraints.NotNull;
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
public class StringQueryItem extends QueryItem {

  private static final long serialVersionUID = -2287578845803526538L;

  @NotNull
  private String value;
  @Builder.Default
  private String operator = ":";

  private String toLowerCase(String str) {
    return "lower(" + str + ")";
  }

  public String toQuery(String path, String alias) {
    StringBuilder query = new StringBuilder(toLowerCase(path));
    switch (operator) {
      case OPERATOR_NOT_EQUAL:
        return query.append(" <> ").append(toLowerCase(":" + alias)).toString();
      case OPERATOR_EQUAL:
        return query.append(" = ").append(toLowerCase(":" + alias)).toString();
      case OPERATOR_CONTAIN:
        return query.append(" like ").append(toLowerCase(":" + alias)).toString();
      case OPERATOR_NOT_CONTAIN:
        return query.append(" not like ").append(toLowerCase(":" + alias)).toString();
      default:
        throw new UnsupportedOperationException(operator + " is not supported for string field");
    }
  }

  public String parameterValue() {
    switch (operator) {
      case OPERATOR_NOT_EQUAL:
      case OPERATOR_EQUAL:
        return value;
      case OPERATOR_CONTAIN:
      case OPERATOR_NOT_CONTAIN:
        return "%" + value + "%";
      default:
        throw new UnsupportedOperationException(operator + " is not supported for string field");
    }
  }
}
