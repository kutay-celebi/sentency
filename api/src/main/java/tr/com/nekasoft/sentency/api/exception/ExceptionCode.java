package tr.com.nekasoft.sentency.api.exception;

import java.util.Collections;
import java.util.Map;
import javax.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionCode {
  UNEXPECTED("SNT-10001", "Unexpected error is occurred.", Response.Status.INTERNAL_SERVER_ERROR),
  DATA_NOT_FOUND("SNT-10002", "Data not found.", Response.Status.NOT_FOUND),
  BAD_CREDENTIAL("SNT-10003", "Bad credentials.", Response.Status.UNAUTHORIZED),
  BAD_REQUEST("SNT-10004", "Bad request", Response.Status.BAD_REQUEST),
  DUPLICATED_DATA("SNT-10005", "Duplicated data.", Response.Status.INTERNAL_SERVER_ERROR),
  EXTERNAL_SERVICE("SNT-10006", "Error is occurred during external service interaction",
      Response.Status.INTERNAL_SERVER_ERROR),
  // USER
  USER_INACTIVE("SNT-10101", "User is inactive", Response.Status.UNAUTHORIZED),
  USER_USERNAME_EXIST("SNT-10102", "Username is already existing",
      Response.Status.INTERNAL_SERVER_ERROR),
  USER_ROLE_ASSIGNED("SNT-10103", "Role is already assigned",
      Response.Status.INTERNAL_SERVER_ERROR),
  // USER WORD
  USER_WORD_ALREADY_EXISTS("SNT-10201", "Word is already added",
      Response.Status.INTERNAL_SERVER_ERROR),
  NO_WORDS_ADDED("SNT-10202", "No word has been added to the list yet", Response.Status.NOT_FOUND);

  private final String code;
  private final String message;
  private final Response.Status status;

  public BusinessException toException(Map<String, String> args) {
    return BusinessException.builder()
        .code(code)
        .status(status)
        .errors(Collections.singletonList(message))
        .args(args)
        .build();
  }

  public BusinessException toException() {
    return toException(Collections.emptyMap());
  }
}
