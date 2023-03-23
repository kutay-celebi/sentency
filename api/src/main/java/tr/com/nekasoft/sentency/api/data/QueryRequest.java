package tr.com.nekasoft.sentency.api.data;

import java.io.Serializable;

import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;

public interface QueryRequest extends Serializable {

    public static String AND = " and ";

    String prepareQuery();

    Parameters prepareParameters();

    Sort prepareSorts();
}
