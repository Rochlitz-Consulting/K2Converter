package org.rochlitz.K2Converter;

import static org.rochlitz.tools.SqlValidator.checkSqlSyntax;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rochlitz.K2Converter.sqlConverter.KopfToSqlConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class K2ConverterIntegrationTest
{


    private static final Logger LOG = LoggerFactory.getLogger(KopfToSqlConverter.class);
    String filePath = "abda_test.sql";//TODO configuration


    @BeforeEach
    public void setUp() {
        System.setProperty("SQL_FILE_PATH", filePath );
        System.setProperty("DB", "laien_info" );
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
            LOG.info("File deleted successfully.");
        } catch (IOException e) {
            LOG.error("Error deleting file", e);
        }
    }

}