package org.rochlitz.K2Converter.sqlConverter;

public class SqlTemplates
{

    //TDOO set UTF-8
    //TDOO add if not exist || TRY CATCH || IFERROR

    public final static String CREATE_SCHEMA_IF_NOT_EXISTS_S = "CREATE SCHEMA  %s";
    public final static String USE = "USE %s";
    public final static String CREATE_TABLE_IF_NOT_EXISTS_ADD_PRIMARY_KEY = "CREATE TABLE  %s (%s BIGINT PRIMARY KEY) ";
    public final static String ALTER_TABLE_S_ADD_IF_NOT_EXISTS_COLUMN = "ALTER TABLE %s ADD   %s ";//TODO add type
    public final static String UTF_8 = " CHARACTER SET utf8";
    public final static String VARCHAR = " VARCHAR(%s)"+UTF_8;
    public final static String TEXT = " TEXT"+UTF_8;
    public final static String MEDIUMTEXT = " MEDIUMTEXT "+UTF_8;
    public final static String BOOLEAN = " BOOLEAN";
    public final static String INT = " INT(%s)";
    public final static String DATE = " DATE";
    public final static String SEMICOLON = ";";
    public final static String INSERT_INTO = "INSERT INTO %s (%s)";
    public final static String INSERT_VALUES = " VALUES (%s)";


}
