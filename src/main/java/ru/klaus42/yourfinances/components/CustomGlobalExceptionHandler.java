package ru.klaus42.yourfinances.components;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // error handle for @Valid
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());

        //Get all errors
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        body.put("errors", errors);

        return new ResponseEntity<>(body, headers, status);

    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        String field = "";
        Throwable throwable = ex.getCause();
        JsonMappingException jsonMappingException = ((JsonMappingException) throwable);
        List<JsonMappingException.Reference> references = ((InvalidFormatException) throwable).getPath();

        var value = ((InvalidFormatException) throwable).getValue();

        for (JsonMappingException.Reference reference : references) {
            if (reference.getFieldName() != null) {
                field += reference.getFieldName() + ".";
            }
        }
        field = field.substring(0, field.length() - 1);
        String message = jsonMappingException.getOriginalMessage();

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());

        body.put("errors", message);
        body.put("field", field);
        body.put("fieldvalue", value);

        return new ResponseEntity<>(body, headers, status);
//        return super.handleHttpMessageNotReadable(ex, headers, status, request);
    }
}
