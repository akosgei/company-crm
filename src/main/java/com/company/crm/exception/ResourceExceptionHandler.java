package com.company.crm.exception;

import com.company.crm.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.CollectionUtils.isEmpty;

@ControllerAdvice
@ResponseBody
@Slf4j
public class ResourceExceptionHandler {

    @ExceptionHandler(value = {ApplicationActivityException.class})
    public ResponseEntity<ErrorResponse> resourceActivityErrorHandler(ApplicationActivityException exception) {
        ErrorResponse response = ErrorResponse.builder()
                .httpStatusCode(exception.getHttpStatus().value())
                .errorMessage(exception.getErrorMessages().getDetailedErrorMessage())
                .responseTime(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, exception.getHttpStatus());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> onConstraintValidationException(ConstraintViolationException constraintViolationException) {
        String errorMessage;
        if (!isEmpty(constraintViolationException.getConstraintViolations())) {
            errorMessage = constraintViolationException.getConstraintViolations().stream().map(
                    violation -> violation.getPropertyPath() + "-" + violation.getMessage()).sorted().collect(Collectors.joining(" | "));
        } else {
            errorMessage = "Unmapped constraint violation has occured.";
        }
        ErrorResponse response = ErrorResponse.builder()
                .httpStatusCode(HttpStatus.BAD_REQUEST.value())
                .errorMessage(errorMessage)
                .responseTime(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleFailedRequests(MethodArgumentNotValidException argumentNotValidException) {

        List<FieldError> errorList = argumentNotValidException.getFieldErrors();
        String errorMsg = errorList.stream()
                .map(errorMessage -> errorMessage.getField() + "-" + errorMessage.getDefaultMessage())
                .sorted() // added so that the error messages can be returned in same order always. helps with validation/testing assertions.
                .collect(Collectors.joining(","));

        log.warn("error message: {}", errorMsg);
        return new ResponseEntity<>(errorMsg, HttpStatus.BAD_REQUEST);
    }
}