package tr.com.nekasoft.sentency.api.exception;

import java.util.Collections;
import java.util.stream.Collectors;

import javax.ws.rs.NotFoundException;

import io.quarkus.hibernate.validator.runtime.jaxrs.ResteasyReactiveViolationException;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.reactive.ClientWebApplicationException;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;
import tr.com.nekasoft.sentency.api.data.error.ErrorResponse;
import tr.com.nekasoft.sentency.api.data.error.ValidationErrorResponse;
import tr.com.nekasoft.sentency.api.data.error.ValidationErrorResponseItem;

@Slf4j
public class ExceptionHandler {
    @ServerExceptionMapper
    public RestResponse<ValidationErrorResponse> mapValidation(ResteasyReactiveViolationException ex) {
        var errors = ex.getConstraintViolations()
                       .stream()
                       .map(err -> ValidationErrorResponseItem.builder()
                                                              .message(err.getMessage())
                                                              .path(String.valueOf(err.getPropertyPath()))
                                                              .value(String.valueOf(err.getInvalidValue()))
                                                              .build())
                       .collect(Collectors.toList());
        ValidationErrorResponse response = ValidationErrorResponse.builder()
                                                                  .errors(errors)
                                                                  .code(ExceptionCode.BAD_REQUEST.getCode())
                                                                  .build();
        log.debug("\n\nValidation Error | {} : {}", response.getUuid(), response);
        return RestResponse.status(RestResponse.Status.BAD_REQUEST, response);
    }

    @ServerExceptionMapper
    public RestResponse<ErrorResponse> mapBusinessEx(BusinessException ex) {
        ErrorResponse resp = ErrorResponse.builder()
                                          .uuid(ex.getUuid())
                                          .code(ex.getCode())
                                          .errors(ex.getErrors())
                                          .args(ex.getArgs())
                                          .build();

        log.error("\n\nError | {}:\nCODE: {}\nMessage: {}\nArguments: {} \n\n", resp.getUuid(), resp.getCode(),
                  resp.getErrors(), ex.getArgs());

        log.debug("Stacktrace | {}", resp.getUuid(), ex);

        return RestResponse.status(ex.getStatus(), resp);
    }

    @ServerExceptionMapper
    public RestResponse<ErrorResponse> mapThrowable(Throwable ex) {
        ErrorResponse resp = ErrorResponse.builder()
                                          .code(ExceptionCode.UNEXPECTED.getCode())
                                          .errors(Collections.singletonList(ExceptionCode.UNEXPECTED.getMessage()))
                                          .build();

        log.error("\n\nError | {} : Detail: {} / {} \n\n", resp.getUuid(), resp.getCode(), ex.getMessage());

        log.debug("Stacktrace | {}", resp.getUuid(), ex);
        log.error("err", ex);
        return RestResponse.status(ExceptionCode.UNEXPECTED.getStatus(), resp);
    }

    @ServerExceptionMapper
    public RestResponse<ErrorResponse> mapClientException(ClientWebApplicationException ex) {
        ErrorResponse resp = ErrorResponse.builder()
                                          .code(ExceptionCode.EXTERNAL_SERVICE.getCode())
                                          .errors(Collections.singletonList(
                                                  ExceptionCode.EXTERNAL_SERVICE.getMessage()))
                                          .build();

        log.error("\n\nError | {} : Detail: {} / {} \n\n", resp.getUuid(), resp.getCode(), ex.getMessage());

        log.debug("Stacktrace | {}", resp.getUuid(), ex);
        log.error("err", ex);
        return RestResponse.status(ExceptionCode.UNEXPECTED.getStatus(), resp);
    }

    @ServerExceptionMapper
    public RestResponse<ErrorResponse> mapClientException(NotFoundException ex) {
        ErrorResponse resp = ErrorResponse.builder()
                                          .code(ExceptionCode.EXTERNAL_SERVICE.getCode())
                                          .errors(Collections.singletonList(
                                                  ExceptionCode.EXTERNAL_SERVICE.getMessage()))
                                          .build();

        log.error("\n\nError | {} : Detail: {} / {} \n\n", resp.getUuid(), resp.getCode(), ex.getMessage());

        log.debug("Stacktrace | {}", resp.getUuid(), ex);
        log.error("err", ex);
        return RestResponse.status(ExceptionCode.UNEXPECTED.getStatus(), resp);
    }
}
