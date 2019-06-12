package com.gmail.kaminski.viktar.onlinemarket.controller.exception.handler;

import com.gmail.kaminski.viktar.onlinemarket.controller.exception.RESTControllerException;
import com.gmail.kaminski.viktar.onlinemarket.controller.exception.WebControllerException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;


@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RESTControllerException.class)
    public ResponseEntity<Object> restErrorHandler(
            RESTControllerException e,
            WebRequest request) {
        return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(WebControllerException.class)
    public String webErrorHandler(HttpServletRequest request, Exception e, Model model) {
        request.setAttribute("message", e.getMessage());
        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String allErrorHandler(HttpServletRequest request, Exception e, Model model) {
        request.setAttribute("message", e.getMessage());
        return "error";
    }
}
