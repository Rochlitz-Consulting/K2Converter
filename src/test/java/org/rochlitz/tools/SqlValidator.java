package org.rochlitz.tools;

import net.sf.jsqlparser.parser.CCJSqlParserUtil;

public class SqlValidator {


    public static void checkSqlSyntax(String sql) {
            try {

//                DSL.using(MYSQL).parser().parse(sql);

                CCJSqlParserUtil.parse(sql);

                //LOG.info("SQL-Statement ist syntaktisch korrekt: " + sql);
            } catch (Exception e) {
                System.err.println("Fehler bei der SQL-Syntaxprüfung: " + e.getMessage() + " für SQL: " + sql);
                 throw new RuntimeException("Fehler bei der SQL-Syntaxprüfung: " + e.getMessage() + " für SQL: " + sql);
            }
    }
}