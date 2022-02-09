package com.dulgi.helper.common;

public enum Regex {
    SETTER("^set[A-Z].*"),
    GETTER("^get[A-Z].*");

    public final String regex;

    private Regex(String regex) { 
        this.regex = regex;
    }
    
    public String getRegex(){
        return regex;
    }


}
