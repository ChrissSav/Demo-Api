package com.example.demo.exception;

import com.example.demo.util.Util;
import com.example.demo.dto.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(value = {ConflictException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public BaseResponse<String> handleApiException(ConflictException conflictException) {
        return Util.generateErrorResponse(conflictException.getCustomMsg());
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public BaseResponse<String> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        
        FieldError fieldError = ex.getBindingResult().getFieldError();
        if (fieldError != null)
            return Util.generateErrorResponse(fieldError.getField() + ", " + fieldError.getDefaultMessage());
        return Util.generateErrorResponse("NOT_VALID_PAYLOAD");

    }

    @ExceptionHandler(value = {JwtException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResponse<String> handleSpringJwtException(JwtException ex) {
        
        return Util.generateErrorResponse(ex.getMessage() + " handleSpringRedditException");
    }

    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public BaseResponse<String> handleMethodArgumentNotValid(HttpMessageNotReadableException ex) {
        
        return Util.generateErrorResponse("NOT_VALID_PAYLOAD, HttpMessageNotReadableException");
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public BaseResponse<String> handleConstraintViolationExceptions(ConstraintViolationException ex) {
        
        return Util.generateErrorResponse(String.format("Invalid input parameters: %s", ex.getMessage()));
    }

    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public BaseResponse<String> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        
        return Util.generateErrorResponse(String.format("%s, invalid input parameter", ex.getName()));

    }

    @ExceptionHandler(value = {InternalAuthenticationServiceException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public BaseResponse<String> handleInternalAuthenticationServiceException(InternalAuthenticationServiceException ex) {
        
        return Util.generateErrorResponse("USER_IS_NOT_AUTHENTICATE");
    }


    @ExceptionHandler(value = {MissingServletRequestParameterException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public BaseResponse<String> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        
        return Util.generateErrorResponse(String.format("%s, invalid input parameter", ex.getParameterName()));

    }

    @ExceptionHandler(value = {BadCredentialsException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public BaseResponse<String> handleBadCredentialsException(BadCredentialsException ex) {
        return Util.generateErrorResponse("AUTH_LOGIN_WRONG_FIELDS");

    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResponse<String> handle(HttpRequestMethodNotSupportedException ex) {
        return Util.generateErrorResponse("HTTP_REQUEST_METHOD_NOT_SUPPORTED_EXCEPTION  Method: " + ex.getMethod());

    }


    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResponse<String> handle(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        ex.printStackTrace();
        return Util.generateErrorResponse("SOMETHING_WRONG");

    }
}