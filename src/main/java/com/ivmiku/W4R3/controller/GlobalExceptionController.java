package com.ivmiku.W4R3.controller;

import com.chenkaiwei.krest.exceptions.KrestAuthenticationException;
import com.chenkaiwei.krest.exceptions.KrestException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionController {


    @ExceptionHandler({KrestAuthenticationException.class,AuthenticationException.class})
    public ResponseEntity KrestAuthenticationExceptionHandler(KrestAuthenticationException e) {
        log.error("krestExceptionHandler");
        log.error(e.getLocalizedMessage());

        Map<String,Object> body=new HashMap<String,Object>();
        body.put("status",HttpStatus.FORBIDDEN.value());
        body.put("message",e.getLocalizedMessage());
        body.put("exception",e.getClass().getName());
        body.put("error",HttpStatus.FORBIDDEN.getReasonPhrase());
        return new ResponseEntity(body, HttpStatus.FORBIDDEN);
    }


    /**
     * 权限验证错误
     * @param e
     * @return
     */
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity unauthorizedExceptionHandler(UnauthorizedException e) {
        log.error("unauthorizedExceptionHandler");
        log.error(e.getLocalizedMessage());

        Map<String,Object> body=new HashMap<String,Object>();
        body.put("status",HttpStatus.UNAUTHORIZED.value());
        body.put("message",e.getLocalizedMessage());
        body.put("exception",e.getClass().getName());
        body.put("error",HttpStatus.UNAUTHORIZED.getReasonPhrase());
        return new ResponseEntity(body, HttpStatus.UNAUTHORIZED);
    }

    /**
     * 普通错误处理
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity exceptionHandler(Exception e) {
        log.error("exceptionHandler");
        log.error(e.getLocalizedMessage());
        log.error(e.getStackTrace().toString());

        Map<String,Object> body=new HashMap<String,Object>();
        body.put("message",e.getLocalizedMessage());
        body.put("exception",e.getClass().getName());
        body.put("error",HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        return new ResponseEntity(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
