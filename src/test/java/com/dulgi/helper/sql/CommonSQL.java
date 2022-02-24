package com.dulgi.helper.sql;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
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
        setMysqlType();
    }

    @DisplayName("enumTypeToSet")
    @Test
    private void enumTypeToSetTest(){
        //given
//        final CommonSQL commonSQL = new CommonSQL();

        //when
//        commonSQL.setMysqlType();
        setMysqlType();

        //then
        assert mysqlType.contains("STRING");

    }

    private void setMysqlType(){
        Arrays.stream(MysqlType.values()).forEach(e->mysqlType.add(e.toString()));

    }

    protected boolean checkType(String type){
        return mysqlType.contains(type);

    }
}
