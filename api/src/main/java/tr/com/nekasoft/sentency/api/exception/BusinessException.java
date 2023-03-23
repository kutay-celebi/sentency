package tr.com.nekasoft.sentency.api.exception;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.ws.rs.core.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = -3227027482337068081L;

    private List<String> errors;
    private String code;
    @Builder.Default
    private Instant time = Instant.now();
    @Builder.Default
    private String uuid = UUID.randomUUID().toString();
    @Builder.Default
    private Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;

    private Map<String, String> args;
}
