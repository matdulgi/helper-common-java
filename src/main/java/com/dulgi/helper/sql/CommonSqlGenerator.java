package com.dulgi.helper.sql;

import com.dulgi.helper.common.Core;
import com.dulgi.helper.entity.DTOEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Set;

public class CommonSqlGenerator {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    private Core core = new Core();
    private Set<String> reservedWordSet;

    public enum Quote {
        SINGLE_QUOTE('\''),
        DOUBLE_QUOTE('\"');

        char c;

        Quote(char c) {
            this.c = c;
        }

        char getQuote() {
            return c;
        }
    }


    //add single or double quotation for String of Date
    public String addQuotes(String str, Quote quote) {
        return quote.getQuote() + str + quote.getQuote();
    }

    // reserved word needs quotation
    public void quoteReservedWord(String word) {
        if (reservedWordSet.contains(word))
            word = "\"" + word + "\"";
    }

    public String getMaxValueSql(String maxColumn, String table) {
        //select max([column]) from [table] where [conditionColumn] = ?
        quoteReservedWord(maxColumn);
        quoteReservedWord(table);
        String sql = "select max(" + maxColumn + ") from " + table;
        logger.info("generated sql : " + sql);
        return sql;
    }

    public String getMaxValueSql(String maxColumn, String table, String conditionColumn) {
        //select max([column]) from [table] where [conditionColumn] = ?
        quoteReservedWord(maxColumn);
        quoteReservedWord(table);
        quoteReservedWord(conditionColumn);
        String sql = "select max(" + maxColumn + ") from " + table + " where " + conditionColumn + " = ?";
        System.out.println("generated sql : " + sql);
        return sql;
    }

    public String getInsertSql(String table, Object dto) {
        DTOEntity entity = new DTOEntity(dto.getClass());

        Set<String> propSet = entity.getPropsNames();

        String columnPart = "";
        String valuePart = "";

        Iterator<String> propSetItr = propSet.iterator();

        while (propSetItr.hasNext()) {
            columnPart = columnPart + dto;
            valuePart = valuePart + "? ";
            if (propSetItr.hasNext()) {
                columnPart = columnPart + ", ";
                valuePart = valuePart + ", ";
            }
        }

        String sql = "insert into post (" + columnPart + ") values (" + valuePart + ")";
        System.out.println("generated sql : " + sql);
        return sql;
    }

    public String getDeleteSql(String table, String conditionColumn) {
        //delete from [테이블] where [조건칼럼 = 값]
        quoteReservedWord(table);
        quoteReservedWord(conditionColumn);
        String sql = "delete from " + table + " where " + conditionColumn + " = ?";
        System.out.println("generated sql : " + sql);
        return sql;
    }
}
