package com.yuvaintern.blogapi.exception;
import jakarta.servlet.http.HttpServletRequest; import jakarta.validation.ConstraintViolationException; import org.springframework.dao.DataIntegrityViolationException; import org.springframework.http.*; import org.springframework.web.bind.MethodArgumentNotValidException; import org.springframework.web.bind.annotation.*; import java.time.Instant; import java.util.*;
@RestControllerAdvice public class GlobalExceptionHandler {
public record ErrorBody(Instant timestamp,int status,String error,String message,String path,Map<String,String> validationErrors){}
@ExceptionHandler(ApiExceptions.NotFound.class) ResponseEntity<ErrorBody> notFound(RuntimeException e,HttpServletRequest r){return make(HttpStatus.NOT_FOUND,e.getMessage(),r,Map.of());}
@ExceptionHandler(ApiExceptions.Conflict.class) ResponseEntity<ErrorBody> conflict(RuntimeException e,HttpServletRequest r){return make(HttpStatus.CONFLICT,e.getMessage(),r,Map.of());}
@ExceptionHandler(MethodArgumentNotValidException.class) ResponseEntity<ErrorBody> invalid(MethodArgumentNotValidException e,HttpServletRequest r){Map<String,String> m=new LinkedHashMap<>();e.getBindingResult().getFieldErrors().forEach(x->m.putIfAbsent(x.getField(),x.getDefaultMessage()));return make(HttpStatus.BAD_REQUEST,"Validation failed",r,m);}
@ExceptionHandler(DataIntegrityViolationException.class) ResponseEntity<ErrorBody> dataConflict(Exception e,HttpServletRequest r){return make(HttpStatus.CONFLICT,"Request conflicts with an existing database record.",r,Map.of());}
@ExceptionHandler(Exception.class) ResponseEntity<ErrorBody> unexpected(Exception e,HttpServletRequest r){return make(HttpStatus.INTERNAL_SERVER_ERROR,"An unexpected server error occurred.",r,Map.of());}
private ResponseEntity<ErrorBody> make(HttpStatus s,String m,HttpServletRequest r,Map<String,String> v){return ResponseEntity.status(s).body(new ErrorBody(Instant.now(),s.value(),s.getReasonPhrase(),m,r.getRequestURI(),v));}
}