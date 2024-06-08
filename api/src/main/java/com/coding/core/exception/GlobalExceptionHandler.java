package com.coding.core.exception;

import com.coding.web.ApiResponse;
import org.springframework.context.MessageSource;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Locale;

import static com.coding.core.constant.MessageKeyConstant.*;
import static com.coding.core.util.StringUtilKt.camelCaseToSnakeCase;
import static java.util.stream.Collectors.*;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex) {
        return ResponseEntity
                .internalServerError()
                .body(ApiResponse.builder()
                        .message(messageSource.getMessage(ERROR_500, null, Locale.getDefault()))
                        .httpStatusCode(INTERNAL_SERVER_ERROR).build());
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<Object> handleDataNotFound(DataNotFoundException ex) {
        return ResponseEntity
                .status(NOT_FOUND)
                .body(ApiResponse.builder().message(ex.getMessage()).httpStatusCode(NOT_FOUND).build());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequest(BadRequestException ex) {
        return ResponseEntity
                .badRequest()
                .body(ApiResponse.builder().message(ex.getMessage()).httpStatusCode(BAD_REQUEST).build());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleUnauthorizedException(BadCredentialsException ex) {
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(ApiResponse.builder()
                        .message(messageSource.getMessage(ERROR_401, null, Locale.getDefault()))
                        .httpStatusCode(UNAUTHORIZED)
                        .build());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException unused) {
        return ResponseEntity
                .status(FORBIDDEN)
                .body(ApiResponse.builder()
                        .message(messageSource.getMessage(ERROR_403, null, Locale.getDefault()))
                        .httpStatusCode(FORBIDDEN)
                        .build());
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<Object> handleUserNotActivatedException(DisabledException ex) {
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(ApiResponse.builder()
                        .message(messageSource.getMessage(ERROR_DISABLED, null, Locale.getDefault()))
                        .httpStatusCode(UNAUTHORIZED)
                        .build());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        final var errors = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(groupingBy(
                        fieldError -> camelCaseToSnakeCase(fieldError.getField()),
                        mapping(DefaultMessageSourceResolvable::getDefaultMessage, toList())
                ));

        return ResponseEntity
                .status(BAD_REQUEST)
                .headers(headers)
                .body(ApiResponse.builder().httpStatusCode(status).error(errors).build());
    }
}
