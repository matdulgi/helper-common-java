package com.dulgi.helper.common;

public enum Regex {
    SETTER("^set[A-Z].*"),
    GETTER("^get[A-Z].*"),
    CAMEL("^[A-Z]+[a-Z][0-9]*"),
    SNAKE("^[a-z]+[_][a-z]"),
    PROPERTY("^[a-z]+[.][a-z]");

    public final String regex;

    Regex(String regex) {
        this.regex = regex;
    }
    
    public String getRegex(){
        return regex;
    }

}
