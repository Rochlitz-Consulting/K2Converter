package org.rochlitz.K2Converter.type.record.types;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 01 Anzahl der D-Sätze
 * 02 Anzahl der I-Sätze
 * 03 Anzahl der U-Sätze
 * 04 Anzahl aller Felder der Datei über alle Satztypen inklusive dieses Feldes
 *
 */
@ToString
@Data
@NoArgsConstructor
public class EndRecord
{

    private Integer dRecordCount;
    private Integer iRecordCount;
    private Integer uRecordCount;
    private Integer totalRecordCount;

}
