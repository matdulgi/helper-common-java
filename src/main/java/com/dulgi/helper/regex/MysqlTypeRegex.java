package com.dulgi.helper.regex;


public enum MysqlTypeRegex {
    /**
     * evaluate order (e.g. varchar can include integer)
     * strong condition type > weak condition type
     * json > int, double, double > varchar
     */
    // integer
    INT("^[-]?[1-2]?\\d{1,9}$"),
    BIGINT("^[-]?\\d{1,19}$"),


    // float
    DOUBLE("^\\d.{0,12}\\d.{0,12}$"),

    // String
//    STRING(""),
//    TEXT(""),
    VARCHAR("^.{1,255}$"),


    /**
     * from mysql 5.7.8, support json data
     */
    JSON("^\\{\".*\":.*}$")
    ;


    String regex;
    int range;

    MysqlTypeRegex(String regex){
       this.regex = regex;
       this.range = 0;
    };
    MysqlTypeRegex(String regex, int range){
        this.regex = regex;
        this.range = range;
    };

    public String getRegex() {
        if(this.equals(VARCHAR) && range > 0){
            return "^.{1," + range + "}$";
        }
        return regex;
    }

    public String getType(){
//        return "`" + this.name() + "`";
        return this.name();
    }
}
