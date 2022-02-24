package com.dulgi.helper.sql;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CommonSQL {
    enum ReservedWord{

    }

    enum MysqlType{
        STRING, VARCHAR, CHAR, TIMESTAMP, INT;
        //and more
    }

    Set<String> mysqlType;

    CommonSQL(){
        mysqlType = new HashSet<>();
        Arrays.stream(MysqlType.values()).forEach(e->mysqlType.add(e.toString()));
    }

    protected boolean checkType(String type){
        return mysqlType.contains(type);

    }
}
