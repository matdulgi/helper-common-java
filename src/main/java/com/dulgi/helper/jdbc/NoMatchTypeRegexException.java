package com.dulgi.helper.jdbc;

public class NoMatchTypeRegexException extends RuntimeException{
    NoMatchTypeRegexException(){}
    NoMatchTypeRegexException(String msg){
        super(msg);
    }

}
