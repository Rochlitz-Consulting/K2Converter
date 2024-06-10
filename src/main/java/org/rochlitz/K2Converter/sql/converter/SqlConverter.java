package org.rochlitz.K2Converter.sql.converter;

import static java.lang.Math.abs;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.rochlitz.K2Converter.type.record.types.FeldRecord;

public class SqlConverter
{

    static void addPrimaryKey(StringBuffer sql, String tableName, FeldRecord feldRecord)
    {
        sql.append(
            String.format(
                SqlTemplates.CREATE_TABLE_S_S_BIGINT_PRIMARY_KEY, tableName,
                feldRecord.getFieldName()
            )
        );
    }
    static void addFieldType(FeldRecord feldRecord, StringBuffer sql)
    {
        final int toleranceBuffer = 10;//TODO configure

        if(String.class == feldRecord.getDataType() ){
            if(feldRecord.getBytes() >= 5000 ){
                sql.append(
                    String.format(
                        SqlTemplates.TEXT
                    )
                );
            }else
            {

                sql.append(
                    String.format(
                        SqlTemplates.VARCHAR,
                        feldRecord.getBytes()+ toleranceBuffer
                    )
                );
            }
        }

        if(Boolean.class == feldRecord.getDataType() ){
            sql.append(
                String.format(
                    SqlTemplates.BOOLEAN,
                    feldRecord.getBytes()
                )
            );
        }
        
        if(Integer.class == feldRecord.getDataType() ){
            convertToINTType(feldRecord, sql, toleranceBuffer);
        }

        if(LocalDateTime.class == feldRecord.getDataType() ){
            sql.append(SqlTemplates.DATE);
        }
        if(LocalDate.class == feldRecord.getDataType() ){  //TODO INSERT INTO example_table (date_field) VALUES (STR_TO_DATE(CONCAT('01', '062024'), '%d%m%Y'));
            sql.append(SqlTemplates.DATE);
        }
    }

    /**
     * Datentyp:
     *
     * TINYINT: Speichert kleine Ganzzahlen von -128 bis 127 und benötigt 1 Byte.
     * SMALLINT: Speichert kleine Ganzzahlen von -32.768 bis 32.767 und benötigt 2 Bytes.
     * MEDIUMINT: Speichert mittlere Ganzzahlen von -8.388.608 bis 8.388.607 und benötigt 3 Bytes.
     * INT: Speichert Ganzzahlen von -2.147.483.648 bis 2.147.483.647 und benötigt 4 Bytes.
     * BIGINT: Speichert große Ganzzahlen von -9.223.372.036.854.775.808 bis 9.223.372.036.854.775.807 und benötigt 8 Bytes.
     * FLOAT: Speichert Gleitkommazahlen mit einfacher Genauigkeit und benötigt 4 Bytes.
     * DOUBLE: Speichert Gleitkommazahlen mit doppelter Genauigkeit und benötigt 8 Bytes.
     *
     * @param feldRecord
     * @param sql
     * @param toleranceBuffer
     */
    private static void convertToINTType(FeldRecord feldRecord, StringBuffer sql, int toleranceBuffer)
    {
        //2,147,483,647
        String intType= SqlTemplates.BIGINT;

        if(abs(feldRecord.getBytes()) ==  1){
            intType = SqlTemplates.TINYINT;
        }

        if(abs(feldRecord.getBytes()) ==  2){
            intType = SqlTemplates.SMALLINT;
        }

        if(abs(feldRecord.getBytes()) ==  3){
            intType = SqlTemplates.MEDIUMINT;
        }

        if(abs(feldRecord.getBytes()) == 4 ){
            intType = SqlTemplates.INT;
        }

        if(abs(feldRecord.getBytes()) >= 5 ){
            intType = SqlTemplates.BIGINT;
        }


        sql.append(
            String.format(
                intType,
                feldRecord.getBytes()+ toleranceBuffer
            )
        );
    }

    static void addFieldName(StringBuffer sql, String tableName, FeldRecord feldRecord)
    {

        sql.append(

            String.format(
                SqlTemplates.ALTER_TABLE_S_ADD_S,
                tableName,
                feldRecord.getFieldName()
            )
        );

    }

}
