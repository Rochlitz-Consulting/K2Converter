package org.rochlitz.K2Converter.type.record.types;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

/**
 * 01 Dateiname, maximal 8-stellig, Datentyp FN1
 * 02 Erweiterung des Dateinamens: GES bei UPD bei Updatedateien
 * 03 Gültigkeitsdatum der aktuellen Lieferung, Datentyp DT8; das Datum bezeichnet den
 *  Zeitpunkt, ab dem die in der betreffenden Datei abgelegten Informationen als „veröffentlicht“ gelten
 *  und in Anwendungssystemen zur Verfügung stehen dürfen.
 * 04 Gültigkeitsdatum der vorangegangenen Lieferung in DT8. In Gesamtdateien ist dieses Feld nicht belegt.
 * 05 Absender: ABDATA PHARMA-DATEN-SERVICE
 * 06 Dateilangname, maximal 20-stellig, Datentyp FN2
 * 07 ABDATA-Dateinummer, maximal 4-stellig, Datentyp NU1
 * 08 Anzahl der auf den Kopfsatz folgenden F-Sätze, maximal 3-stellig, Datentyp NU1
 *
 */
@ToString
@Data
@NoArgsConstructor
public class KopfRecord {
    public final static Integer MAX_FIELD_SIZE = 1000000; // 1 mil ascii chars == 600 A4

    private String tableName;//TODO if präfix tablename = 6 chars -> FK ???

    //Otherwise full update
    private Boolean isFull;


    @NonNull
    private LocalDateTime currentDeliveryValidityDate;//JJJJMMTT

    //JJJJMMTT
    /**is empty in GES /Gesamtdateien*/
    @NonNull
    private LocalDateTime previousDeliveryValidityDate;
    private String dataSource;
    private String filenameLong;
    private String adbaFileNumber;
    private int countKRecords;





}
