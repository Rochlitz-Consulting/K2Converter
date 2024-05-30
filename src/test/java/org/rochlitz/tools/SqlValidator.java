package org.rochlitz.tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.opentest4j.TestAbortedException;

public class SqlValidator {

    public static void checkSqlSyntax(Connection connection, String sql) {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            // Syntaxprüfung durch PreparedStatement-Analyse
            statement.close();
            System.out.println("SQL-Statement ist syntaktisch korrekt: " + sql);
        } catch (SQLException e) {
            System.err.println("Fehler bei der SQL-Syntaxprüfung: " + e.getMessage() + " für SQL: " + sql);
            throw new TestAbortedException("Test Failed");
        }
    }
}