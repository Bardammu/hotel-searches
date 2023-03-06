package com.mindata.hotelsearches.validation;

import com.mindata.hotelsearches.model.response.SimpleMessageResponse;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.apache.logging.log4j.LogManager.getLogger;
import static org.springframework.http.ResponseEntity.badRequest;

@RestControllerAdvice
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {

    Logger logger = getLogger(GlobalControllerExceptionHandler.class);

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        logger.error("Request {} raided{}", ((ServletWebRequest)request).getRequest().getRequestURI(), ex);

        String bodyOfResponse;
        if (ex.getFieldErrorCount() > 0) {
            bodyOfResponse = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        } else  {
            bodyOfResponse = ex.getBindingResult().getGlobalErrors().get(0).getDefaultMessage();
        }

        return badRequest()
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(new SimpleMessageResponse(bodyOfResponse));
    }

}
