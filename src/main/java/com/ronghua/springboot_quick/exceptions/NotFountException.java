package com.ronghua.springboot_quick.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFountException extends RuntimeException{
    public NotFountException(){
        super();
    }

    public NotFountException(String message){
        super(message);
    }
}
