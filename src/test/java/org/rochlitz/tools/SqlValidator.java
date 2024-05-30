package org.rochlitz.tools;

import org.jooq.impl.DSL;
import static org.jooq.SQLDialect.*;


public class SqlValidator {


    public static void checkSqlSyntax(String sql) {
            try {

                DSL.using(MYSQL).parser().parse(sql);

                System.out.println("SQL-Statement ist syntaktisch korrekt: " + sql);
            } catch (Exception e) {
                System.err.println("Fehler bei der SQL-Syntaxprüfung: " + e.getMessage() + " für SQL: " + sql);
                 throw new RuntimeException("Fehler bei der SQL-Syntaxprüfung: " + e.getMessage() + " für SQL: " + sql);
            }
    }
}