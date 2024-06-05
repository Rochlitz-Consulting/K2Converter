package org.rochlitz.K2Converter.sqlConverter;

import static java.lang.Math.abs;
import static org.rochlitz.K2Converter.sqlConverter.SqlTemplates.ALTER_TABLE_S_ADD_S;
import static org.rochlitz.K2Converter.sqlConverter.SqlTemplates.BOOLEAN;
import static org.rochlitz.K2Converter.sqlConverter.SqlTemplates.CREATE_TABLE_S_S_BIGINT_PRIMARY_KEY;
import static org.rochlitz.K2Converter.sqlConverter.SqlTemplates.DATE;
import static org.rochlitz.K2Converter.sqlConverter.SqlTemplates.TEXT;
import static org.rochlitz.K2Converter.sqlConverter.SqlTemplates.VARCHAR;

import java.time.LocalDateTime;

import org.rochlitz.K2Converter.toTypeConverter.FeldRecord;

public class SqlConverter
{

    static void addPrimaryKey(StringBuffer sql, String tableName, FeldRecord feldRecord)
    {
        sql.append(
            String.format(
                CREATE_TABLE_S_S_BIGINT_PRIMARY_KEY, tableName,
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
                        TEXT
                    )
                );
            }else
            {

                sql.append(
                    String.format(
                        VARCHAR,
                        feldRecord.getBytes()+ toleranceBuffer
                    )
                );
            }
        }

        if(Boolean.class == feldRecord.getDataType() ){
            sql.append(
                String.format(
                    BOOLEAN,
                    feldRecord.getBytes()
                )
            );
        }
        
        if(Integer.class == feldRecord.getDataType() ){
            convertToINTType(feldRecord, sql, toleranceBuffer);
        }

        if(LocalDateTime.class == feldRecord.getDataType() ){  //TODO add type
            sql.append(DATE);
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
        String intType= "TINYINT";

        if(abs(feldRecord.getBytes()) ==  2){
            intType = "SMALLINT";
        }

        if(abs(feldRecord.getBytes()) ==  3){
            intType = "MEDIUMINT";
        }

        if(abs(feldRecord.getBytes()) == 4 ){
            intType = "INT";
        }

        if(abs(feldRecord.getBytes()) > 5 ){
            intType = "BIGINT";
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
                ALTER_TABLE_S_ADD_S,
                tableName,
                feldRecord.getFieldName()
            )
        );

    }

}
