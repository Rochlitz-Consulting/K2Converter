package org.rochlitz.tools;

import org.jooq.impl.DSL;
import static org.jooq.SQLDialect.*;

import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;

public class SqlValidator {


    public static void checkSqlSyntax(String sql) {
            try {

//                DSL.using(MYSQL).parser().parse(sql);

                Statement parse = CCJSqlParserUtil.parse(sql);

                //System.out.println("SQL-Statement ist syntaktisch korrekt: " + sql);
            } catch (Exception e) {
                System.err.println("Fehler bei der SQL-Syntaxprüfung: " + e.getMessage() + " für SQL: " + sql);
                 throw new RuntimeException("Fehler bei der SQL-Syntaxprüfung: " + e.getMessage() + " für SQL: " + sql);
            }
    }
}