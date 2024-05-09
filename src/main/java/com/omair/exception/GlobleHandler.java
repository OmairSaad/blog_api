package com.omair.exception;

import java.nio.file.FileAlreadyExistsException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.omair.Payloads.Apiresponse;

@RestControllerAdvice
public class GlobleHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Apiresponse> ResourceNotFound(ResourceNotFoundException ex){
    String message = ex.getMessage();
    Apiresponse apiRespone = new Apiresponse(message, false);
    return new ResponseEntity<Apiresponse>(apiRespone, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> excep(MethodArgumentNotValidException ex){
        Map<String,String> resp = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((e)->{
            String error =((FieldError)e).getField();
            String message = e.getDefaultMessage();
            resp.put(error,message);
        });
        return new ResponseEntity<Map<String,String>>(resp, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FileAlreadyExistsException.class)
    public ResponseEntity<Apiresponse> fileExist(FileAlreadyExistsException fx){
        return new ResponseEntity<>(new Apiresponse("File Already Saved with this name", false), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<String> handleNoResourceFoundException() {
        String message = "<h1>Image not Found</h1>";
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Apiresponse> Illegal(IllegalArgumentException ex){
        String message = ex.getMessage();
        Apiresponse apiRespone = new Apiresponse(message, false);
        return new ResponseEntity<Apiresponse>(apiRespone, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public String ff(BadCredentialsException ex){
        return ex.getMessage();
    }

}
