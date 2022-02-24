package com.dulgi.helper.sql;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/** Mysql Create Table Statement Example
 * CREATE TABLE IF NOT EXISTS `SCHEMA`.`TABLE_NAME` (     ---- head statement -----
 *   `COLUMN1` INT NOT NULL,
 *   `COLUMN2` INT NOT NULL,                                   column statement
 *   `COLUMN3` VARCHAR(50) NOT NULL,                      ----------------------------
 *   PRIMARY KEY (`COLUMN1`),                             ------ pk statement --------
 *   CONSTRAINT `fk_FOREIGN_TABLE_COLUMN`                 ----------------------------
 *     FOREIGN KEY (`COLUMN2`)
 *     REFERENCES `SCHEMA`.`TABLE` (`COLUMN`)                  fk statement
 *     ON DELETE NO ACTION
 *     ON UPDATE NO ACTION)                               ----------------------------
 * ENGINE = InnoDB DEFAULT CHARSET=utf8;                  -----additional statement----
 *
 * Requires
 *  - schema, table name
 *  - at least 1 column
 *
 *
 * Todo
 * Index
 * on action
 * autoIncrementColumn
 *
 */
public class CreateSQL extends CommonSQL{
    private String tableName;
    private String schema;
    //column name, column metadata{Type, column Constraints}
    private Map<String, List<String>> columns;
    private Map<String,String> constraints;
    private String[] pk;
    private String[] fk;
    private String engine;
    private String charset = "utf8";


//    public CreateSQL(){};
//    CreateSQL(Object dto){
//    }

    public CreateSQL(String tableName){
        this.tableName = tableName;

    }


    public CreateSQL setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public CreateSQL addColumn(String column, String type, List<String> metadata){
        checkType(type);
        columns.put(column, metadata);
        return this;
    }

    public void addConstraint(String constraint, String metadata){
        constraints.put(constraint, metadata);
    }

    public void setPk(String[] pk){
        this.pk = pk;
    }

    public void setFk(String[] fk) {
        this.fk = fk;
    }

    private String genHeadStatement(){
        String tableName = this.tableName;
        if (!schema.equals("")) {
            tableName = tableName + "." + schema;
        }
            return "create table if not exists " + tableName ;
    }

    private String genColumnStatement(){
        String statement = "";
        Iterator<Map.Entry<String, List<String>>> itr = columns.entrySet().iterator();
        while(itr.hasNext()) {
            Map.Entry<String, List<String>> column = itr.next();
            statement = statement + column.getKey() + " ";
                List<String> columnMetadataList = (List<String>) column.getValue();
                Iterator<String> colConstItr = columnMetadataList.iterator();
                while(colConstItr.hasNext()){
                    String columnMetadata = colConstItr.next();
                    statement = statement + columnMetadata + " ";
                }
            if(itr.hasNext()){
                statement = statement + ",";
            }
        }
        return "(" + statement + ")";
    }

    private String genPKStatement(){
        String pks = "";
        int cnt = pk.length;
        for(int i = 0; i < cnt; i++){
            pks += pk[i] + " ";
            if (i < cnt - 1 ){

            }
        }

        return "primary key ( " + " )";
    }

    private String genFKStatement(){
        Arrays.stream(fk);
        return "";
    }

    public String getCreateSQL() {
//        if (tableName.equals("") ){
//            throw new DDLException("no table name");
//        }

        if (columns.size()==0){
            throw new DDLException("no column");
        }

        return genHeadStatement() + genColumnStatement() + genPKStatement() + genFKStatement();


    }


}


