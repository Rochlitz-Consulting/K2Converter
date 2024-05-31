package org.rochlitz.K2Converter;

import static org.rochlitz.tools.SqlValidator.checkSqlSyntax;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class K2ConverterIntegrationTest
{

    String filePath = "abda_test.sql";//TODO configuration

    @BeforeEach
    public void setUp() {
        deleteSqlOutput(filePath);
    }

    @Test
    public void convert() throws Exception
    {
        K2Converter k2Converter = new K2Converter();
        k2Converter.main(new String[] {});



        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                checkSqlSyntax(line);
            }
        } catch (IOException e) {
            System.err.println("Fehler beim Lesen der Datei: " + e.getMessage());
            throw new Exception("Fehler beim Lesen der Datei: " + e.getMessage());
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