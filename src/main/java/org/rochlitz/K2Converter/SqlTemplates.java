package org.rochlitz.K2Converter;

public class SqlTemplates
{

    //TDOO set UTF-8
    public final static String CREATE_TABLE_IF_NOT_EXISTS = "CREATE TABLE IF NOT EXISTS %s ;";
    public final static String ALTER_TABLE_S_ADD_IF_NOT_EXISTS_COLUMN = "ALTER TABLE %s ADD IF NOT EXISTS COLUMN %s;";//TODO add type
    public final static String ALTER_TABLE_S_ADD_CONSTRAINT_S_PRIMARY_KEY = "ALTER TABLE %s ADD CONSTRAINT %s PRIMARY KEY (%s);";



}
