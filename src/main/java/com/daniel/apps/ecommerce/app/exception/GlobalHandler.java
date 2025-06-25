package com.daniel.apps.ecommerce.app.exception;

import com.daniel.apps.ecommerce.app.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalHandler {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpResponse<Object>> handleException(Exception exp) {

//        exp.printStackTrace();
        log.error("[SERVER ERROR] : {}", exp.getMessage());
        var messageExp = exp.getMessage();
        HttpResponse<Object> httpResponse = HttpResponse.builder().build();
        if (messageExp.contains("Required request body is missing")) {
            httpResponse.setMessage("Please Put the Required Body");
            httpResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            httpResponse.setStatus(HttpStatus.BAD_REQUEST);
        } else {
            httpResponse.setMessage(exp.getMessage());
            httpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            httpResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        httpResponse.setTimeStamp(LocalDateTime.now());
        return ResponseEntity.internalServerError().body(httpResponse);
    }

    @ExceptionHandler(NoSuchProductException.class)
    public ResponseEntity<HttpResponse<Object>> handleNoSuchProductException(NoSuchProductException exp) {
        log.error("[CLIENT ERROR] : {}", exp.getMessage());
        HttpResponse<Object> httpResponse = HttpResponse.builder().build();
        httpResponse.setMessage(exp.getMessage());
        httpResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        httpResponse.setStatus(HttpStatus.BAD_REQUEST);
        httpResponse.setTimeStamp(LocalDateTime.now());
        return ResponseEntity.badRequest().body(httpResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<HttpResponse<Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exp) {
        log.error("[CLIENT ERROR] : {}", exp.getMessage());
        Map<String, String> errors = new HashMap<>();
        exp.getBindingResult().getAllErrors().forEach(objectError -> {
            String field = ((FieldError) objectError).getField();
            String msg = objectError.getDefaultMessage();
            errors.put(field, msg);
        });
        HttpResponse<Object> httpResponse = HttpResponse.builder().build();
        httpResponse.setMessage("field errors");
        httpResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        httpResponse.setStatus(HttpStatus.BAD_REQUEST);
        httpResponse.setTimeStamp(LocalDateTime.now());
        httpResponse.setErrors(errors);
        return ResponseEntity.badRequest().body(httpResponse);
    }

    @ExceptionHandler(NoSuchCategoryException.class)
    public ResponseEntity<HttpResponse<Object>> handleNoSuchCategoryException(NoSuchCategoryException exp) {
        log.error("[CLIENT ERROR] : {}", exp.getMessage());
        HttpResponse<Object> httpResponse = HttpResponse.builder().build();
        httpResponse.setMessage(exp.getMessage());
        httpResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        httpResponse.setStatus(HttpStatus.BAD_REQUEST);
        httpResponse.setTimeStamp(LocalDateTime.now());
        return ResponseEntity.badRequest().body(httpResponse);
    }

    @ExceptionHandler(NoSuchUserException.class)
    public ResponseEntity<HttpResponse<Object>> handleNoSuchUserException(NoSuchUserException exp) {
        log.error("[CLIENT ERROR] : {}", exp.getMessage());
        HttpResponse<Object> httpResponse = HttpResponse.builder().build();
        httpResponse.setMessage(exp.getMessage());
        httpResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        httpResponse.setStatus(HttpStatus.BAD_REQUEST);
        httpResponse.setTimeStamp(LocalDateTime.now());
        return ResponseEntity.badRequest().body(httpResponse);
    }

    @ExceptionHandler(NoSuchAddressException.class)
    public ResponseEntity<HttpResponse<Object>> handleNoSuchAddressException(NoSuchAddressException exp) {
        log.error("[CLIENT ERROR] : {}", exp.getMessage());
        HttpResponse<Object> httpResponse = HttpResponse.builder().build();
        httpResponse.setMessage(exp.getMessage());
        httpResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        httpResponse.setStatus(HttpStatus.BAD_REQUEST);
        httpResponse.setTimeStamp(LocalDateTime.now());
        return ResponseEntity.badRequest().body(httpResponse);
    }

    @ExceptionHandler(NoSuchOrderException.class)
    public ResponseEntity<HttpResponse<Object>> handleNoSuchOrderException(NoSuchOrderException exp) {
        log.error("[CLIENT ERROR] : {}", exp.getMessage());
        HttpResponse<Object> httpResponse = HttpResponse.builder().build();
        httpResponse.setMessage(exp.getMessage());
        httpResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        httpResponse.setStatus(HttpStatus.BAD_REQUEST);
        httpResponse.setTimeStamp(LocalDateTime.now());
        return ResponseEntity.badRequest().body(httpResponse);
    }


    @ExceptionHandler(ProductQuantityExceededException.class)
    public ResponseEntity<HttpResponse<Object>> handleProductQuantityExceededException(ProductQuantityExceededException exp) {
        log.error("[CLIENT ERROR] : {}", exp.getMessage());
        HttpResponse<Object> httpResponse = HttpResponse.builder().build();
        httpResponse.setMessage(exp.getMessage());
        httpResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        httpResponse.setStatus(HttpStatus.BAD_REQUEST);
        httpResponse.setTimeStamp(LocalDateTime.now());
        return ResponseEntity.badRequest().body(httpResponse);
    }

    @ExceptionHandler(EmailFailException.class)
    public ResponseEntity<HttpResponse<Object>> handleEmailFailException(EmailFailException exp) {
        log.error("[CLIENT ERROR] : {}", exp.getMessage());
        HttpResponse<Object> httpResponse = HttpResponse.builder().build();
        httpResponse.setMessage(exp.getMessage());
        httpResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        httpResponse.setStatus(HttpStatus.BAD_REQUEST);
        httpResponse.setTimeStamp(LocalDateTime.now());
        return ResponseEntity.badRequest().body(httpResponse);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<HttpResponse<Object>> handleNoSuchToken(NoSuchToken exp) {
        log.error("[CLIENT ERROR] : {}", exp.getMessage());
        HttpResponse<Object> httpResponse = HttpResponse.builder().build();
        httpResponse.setMessage(exp.getMessage());
        httpResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        httpResponse.setStatus(HttpStatus.BAD_REQUEST);
        httpResponse.setTimeStamp(LocalDateTime.now());
        return ResponseEntity.badRequest().body(httpResponse);
    }


    @ExceptionHandler(NoSuchToken.class)
    public ResponseEntity<HttpResponse<Object>> handleUsernameNotFoundException(UsernameNotFoundException exp) {
        log.error("[CLIENT ERROR] : {}", exp.getMessage());
        HttpResponse<Object> httpResponse = HttpResponse.builder().build();
        httpResponse.setMessage(exp.getMessage());
        httpResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        httpResponse.setStatus(HttpStatus.BAD_REQUEST);
        httpResponse.setTimeStamp(LocalDateTime.now());
        return ResponseEntity.badRequest().body(httpResponse);
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<HttpResponse<Object>> handleFileNotFoundException(FileNotFoundException exp) {
        log.error("[CLIENT ERROR] : {}", exp.getMessage());
        HttpResponse<Object> httpResponse = HttpResponse.builder().build();
        httpResponse.setMessage(exp.getMessage());
        httpResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        httpResponse.setStatus(HttpStatus.BAD_REQUEST);
        httpResponse.setTimeStamp(LocalDateTime.now());
        return ResponseEntity.badRequest().body(httpResponse);
    }

    @ExceptionHandler(MyIOException.class)
    public ResponseEntity<HttpResponse<Object>> handleMyIOException(MyIOException exp) {
        log.error("[CLIENT ERROR] : {}", exp.getMessage());
        HttpResponse<Object> httpResponse = HttpResponse.builder().build();
        httpResponse.setMessage(exp.getMessage());
        httpResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        httpResponse.setStatus(HttpStatus.BAD_REQUEST);
        httpResponse.setTimeStamp(LocalDateTime.now());
        return ResponseEntity.badRequest().body(httpResponse);
    }
    @ExceptionHandler(AuthHeaderRequired.class)
    public ResponseEntity<HttpResponse<Object>> handleAuthHeaderRequired(AuthHeaderRequired exp) {
        log.error("[CLIENT ERROR] : {}", exp.getMessage());
        HttpResponse<Object> httpResponse = HttpResponse.builder().build();
        httpResponse.setMessage(exp.getMessage());
        httpResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        httpResponse.setStatus(HttpStatus.BAD_REQUEST);
        httpResponse.setTimeStamp(LocalDateTime.now());
        return ResponseEntity.badRequest().body(httpResponse);
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<HttpResponse<Object>> handleDataIntegrityViolationException(DataIntegrityViolationException exp) {

        log.error("[CLIENT ERROR] : {}", exp.getMessage());
        var message = exp.getMessage();
        if (message.contains("app_user_email_key")) {
            message = "Email taken";
        }
        HttpResponse<Object> httpResponse = HttpResponse.builder().build();
        httpResponse.setMessage(message);
        httpResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        httpResponse.setStatus(HttpStatus.BAD_REQUEST);
        httpResponse.setTimeStamp(LocalDateTime.now());
        return ResponseEntity.badRequest().body(httpResponse);
    }


}
