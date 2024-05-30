package org.rochlitz.K2Converter;


import static org.rochlitz.tools.SqlValidator.checkSqlSyntax;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opentest4j.TestAbortedException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class K2ConverterIntegrationTest
{

    String filePath = "abda.sql";//TODO configuration
    private static final String URL = "jdbc:mysql://localhost:3306/LAIENINFO"; // URL zu Ihrer MySQL-Datenbank
    private static final String USER = "root"; // Ihr MySQL-Benutzername
    private static final String PASSWORD = "admin"; // Ihr MySQL-Passwort

    @BeforeEach
    public void setUp() {
        deleteSqlOutput(filePath);
    }

    @Test
    public void convert() throws Exception
    {
        K2Converter k2Converter = new K2Converter();
        k2Converter.main(new String[] {});
        try (BufferedReader br = new BufferedReader(new FileReader(filePath));
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {

            String line;
            while ((line = br.readLine()) != null) {
                checkSqlSyntax(connection, line);
            }
        } catch (IOException | SQLException e) {
            System.err.println("Fehler: " + e.getMessage());
            throw new TestAbortedException("Connection Failed");
        }


    }

    public void deleteSqlOutput(String filePath) {
        try {
            Path path = Paths.get(filePath);

            Files.delete(path);
            System.out.println("File deleted successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}