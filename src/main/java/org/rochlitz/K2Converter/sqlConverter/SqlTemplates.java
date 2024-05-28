package org.rochlitz.K2Converter.sqlConverter;

public class SqlTemplates
{

    //TDOO set UTF-8
    //TDOO add if not exsit
    public final static String CREATE_SCHEMA_IF_NOT_EXISTS_S = "CREATE SCHEMA  %s;";
    public final static String USE = "USE %s;";
    public final static String CREATE_TABLE_IF_NOT_EXISTS_ADD_PRIMARY_KEY = "CREATE TABLE  %s (%s INT PRIMARY KEY);";
    public final static String ALTER_TABLE_S_ADD_IF_NOT_EXISTS_COLUMN = "ALTER TABLE %s ADD   %s ";//TODO add type
    public final static String VARCHAR = " VARCHAR(%s)";
    public final static String BOOLEAN = " BOOLEAN";
    public final static String INT = " INT(%s)";
    public final static String DATE = " DATE";
    public final static String SEMICOLON = " ;";
//    public final static String ALTER_TABLE_S_ADD_IF_NOT_EXISTS_COLUMN = "INSERT INTO %s (column1, column2, ...)\n"
//        + "VALUES (value1, value2, ...);\n   %s ";//TODO add type

}
