package tr.com.nekasoft.sentency.api.data;

import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import java.io.Serializable;

public interface QueryRequest extends Serializable {

  public static String AND = " and ";

  String prepareQuery();

  Parameters prepareParameters();

  Sort prepareSorts();
}
