package com.dulgi.helper.jdbc;

import com.dulgi.helper.annotation.NeedToChange;
import com.dulgi.helper.common.Core;
import com.dulgi.helper.regex.MysqlTypeRegex;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.regex.Pattern;

public class JDBCFunction {
    Core core = new Core();
    String dbType;

    private final int TIMESTAMP_LENGTH = 10 ;

    @NeedToChange("remove noarg constructor")
    JDBCFunction(){}

    public JDBCFunction(String dbType){
        this.dbType = dbType;
    }

    // setter나 getter메서드의 이름을 칼럼명으로 변경. 칼럼명은 대문자로 튀어나오기 때문에 대문자로 지정
    public String methodNameToColumn(String methodName) {
        return methodName.substring(3).toUpperCase();
    }


    // in some case, like jdbc, SQL Type NUMBER is converted to BicDecimal by getObject, so it's needs to cast to Integer
    public Object convertColumnType(Object columnValue) {
        String type = columnValue.getClass().getSimpleName();
        Object convertedValue = null;
        switch (type) {
            case "BigDecimal":
                BigDecimal bigDecimal = (BigDecimal) columnValue;
                convertedValue = bigDecimal.intValue();
                break;
            default:
                convertedValue = columnValue;
        }
        return convertedValue;
    }

    @NeedToChange("Mysql only now")
    // sequence of evaluate can be changed
    public String evalTypeRegex(String value){
        if(Pattern.matches(MysqlTypeRegex.JSON.getRegex(), value)){
            return MysqlTypeRegex.JSON.getType();
        } else if(Pattern.matches(MysqlTypeRegex.INT.getRegex(), value)){
            return MysqlTypeRegex.INT.getType();
        } else if(Pattern.matches(MysqlTypeRegex.DOUBLE.getRegex(), value)){
            return MysqlTypeRegex.DOUBLE.getType();
        }
        if(Pattern.matches(MysqlTypeRegex.BIGINT.getRegex(), value)){
            return MysqlTypeRegex.BIGINT.getType();
        }
        if(Pattern.matches(MysqlTypeRegex.VARCHAR.getRegex(), value)){
            return MysqlTypeRegex.VARCHAR.getType();
        }
        throw new NoMatchTypeRegexException();
    }


    // dto와 칼럼이름, 값만 가지고오면 알아서 매칭해서 저장
    // Collection 변수이면 add하기 추가....
    public void setPropertyWithColumn(Object dto, String column, Object columnValue) {
        // dto에서 메서드 이름 뽑아오기
        Class clazz = dto.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        String property;
        String methodName = "";
        Object convertedColumnValue = columnValue;


        for (Method method : methods) {
            try {
                methodName = method.getName();
                property = methodNameToColumn(methodName);
//				if (methodName.contains("set") && property.equals(column) && columnValue!=null) {// 칼럼이름은 대문자로 튀어나온다
                if (methodName.contains("set") && property.contains(column) && columnValue != null) {// 칼럼이름은 대문자로 튀어나온다
                    convertedColumnValue = convertColumnType(columnValue);
                    System.out.println("칼럼 " + column + "과 일치하는 메서드 발견 : " + methodName + "실행");
                    System.out.println(property + "의 값을 " + convertedColumnValue + "로 초기화");
                    method.invoke(dto, convertedColumnValue);
                }
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }


    public static Object castParamType(String paramValue, Object propType) {
        Object result = null;
        switch (propType.getClass().getSimpleName()) {
            case "String":
                return paramValue;
            case "Integer":
                return Integer.parseInt(paramValue);
            case "Double":
                return Double.parseDouble(paramValue);
            case "Boolean":
                return Boolean.parseBoolean(paramValue);
            case "Date":
                return java.sql.Date.valueOf(paramValue);
        }
        return "result";
    }

    public static Object castParamType(String[] paramValue, Object propType) {
        Object result = null;
        switch (propType.getClass().getSimpleName()) {
            case "String":
                return paramValue;
            case "Integer":
                return Integer.parseInt(paramValue[0]);
            case "Double":
                return Double.parseDouble(paramValue[0]);
            case "Boolean":
                return Boolean.parseBoolean(paramValue[0]);
            case "Date":
                return java.sql.Date.valueOf(paramValue[0]);
        }
        return "result";
    }


    public String convertUnixTimeFormat(String str){
        if (str.length()==TIMESTAMP_LENGTH) str = String.valueOf(Long.valueOf(str) *1000);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+9"));
        return sdf.format(Long.parseLong(str));
    }

}
