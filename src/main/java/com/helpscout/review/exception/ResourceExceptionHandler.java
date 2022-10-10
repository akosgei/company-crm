package com.helpscout.review.exception;

import com.helpscout.review.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;

@ControllerAdvice
@ResponseBody
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
}
