package com.lcwd.electronic.store.exceptions;

import com.lcwd.electronic.store.dtos.ApiResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.NoSuchFileException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseMessage> resourceNotFoundException(ResourceNotFoundException ex){

        ApiResponseMessage build = ApiResponseMessage.builder().message(ex.getMessage()).httpStatus(HttpStatus.NOT_FOUND).success(true).build();
        return new ResponseEntity<>(build,HttpStatus.NOT_FOUND);

    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<Map<String,Object>> handleMethodArgument(MethodArgumentNotValidException ex){
//        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
//        Map<String,Object> response=new HashMap<>();
//        allErrors.stream().forEach(objectError -> {
//            String message=objectError.getDefaultMessage();
//            String field=((FieldError) objectError).getField();
//            response.put(message,field);
//
//        });
//        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
//
//    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<Map<String,Object>> handleMethodArgument(MethodArgumentNotValidException ex){
//        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
//        Map<String,Object> response=new HashMap<>();
//        allErrors.stream().forEach(objectError ->{
//            String message=objectError.getDefaultMessage();
//            String field=((FieldError) objectError).getField();
//            response.put(message,field);
//        });
//        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
//
//    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> handleMethodArgument(MethodArgumentNotValidException ex){
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        Map<String,Object> response=new HashMap<>();
        allErrors.stream().forEach(objectError ->{
            String message=objectError.getDefaultMessage();
            String field=((FieldError) objectError).getField();
            response.put(message,field);
        });

        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadApiRequest.class)
    public ResponseEntity<ApiResponseMessage> getBadRequest(BadApiRequest ex){
        ApiResponseMessage build = ApiResponseMessage.builder().message(ex.getMessage()).httpStatus(HttpStatus.BAD_REQUEST).success(true).build();
        return new ResponseEntity<>(build,HttpStatus.BAD_REQUEST);

    }
//    @ExceptionHandler(NoSuchFileException.class)
//    public ResponseEntity<ApiResponseMessage> handleNoSuchFileException(NoSuchFileException ex){
//        ApiResponseMessage build = ApiResponseMessage.builder().message(ex.getMessage()).success(false).httpStatus(HttpStatus.NOT_FOUND).build();
//        return new ResponseEntity<>(build,HttpStatus.NOT_FOUND);
//    }
}
