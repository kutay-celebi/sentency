package tr.com.nekasoft.sentency.api.data;

import java.io.Serializable;

public abstract class QueryItem implements Serializable {
    private static final long serialVersionUID = -6481133708871944738L;

    public static final String OPERATOR_EQUAL = ":";
    public static final String GREATER_THAN = ">=";
    public static final String GREATER = ">";
    public static final String LESS_THAN = "<=";
    public static final String LESS = "<";
    public static final String OPERATOR_NOT_EQUAL = "!:";
    public static final String OPERATOR_CONTAIN = "~";
    public static final String OPERATOR_NOT_CONTAIN = "!~";
}
