package com.dulgi.helper.common;

interface StringConverter{
    int ASCII_CASE_DIFF = 32;
    int ASCII_MIN_LO_CASE = 97; 
    int ASCII_MAX_LO_CASE = 122;
    int ASCII_MIN_UP_CASE = 65;
    int ASCII_MAX_UP_CASE = 91;

    boolean isCamelStyle(String str);
    boolean isPropertyStyle(String str);
    boolean isSnakeStyle(String str);

    // seperated Word : snake, property  
    String sepWdToCamel(String str, char delimiter); 
    String camelToSepWd(String str, char delimiter);

}