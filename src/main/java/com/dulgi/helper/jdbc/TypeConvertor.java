package com.dulgi.helper.jdbc;

import com.dulgi.helper.annotation.NeedToChange;

import java.sql.Timestamp;


@NeedToChange("this class can be private module in Entity or SQL")
/** MYSQL, JDBC Type Matching
 * match the type between RDBMS(MYSQL) and JDBC
 * # NUMBER
 * - TINYINT
 *
 * # FLOAT
 *
 * # STRING
 * - CHAR, VARCHAR : STRING
 * - TINYTEXT, TEXT, LONGTEXT : STRING
 *
 * # DATE
 * - DATE, TIMESTAMP : Timestamp
 */
//class TypeConvertor{
public class TypeConvertor {

    public Integer stringToInteger(String str){
        return Integer.parseInt(str);
    }

    public Double stringToDouble(String str){
        return Double.parseDouble(str);
    }

    public Timestamp stringToTimestamp(String str){
        return Timestamp.valueOf(str);
    }

}
