package org.rochlitz.K2Converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rochlitz.K2Converter.sql.converter.KopfToSqlConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class K2ConverterIntegrationTest
{


    private static final Logger LOG = LoggerFactory.getLogger(KopfToSqlConverter.class);
    private final String filePath = "abda_test.sql";//TODO configuration


    @BeforeEach
    public void setUp() {

        deleteSqlOutput(filePath);
    }

    @Test
    public void convert() throws Exception
    {


        String[] args = { "-i=src/test/resources/integrationtest",
            "-d=laien_info",
            "-o="+filePath
            };
        Application.main(args);

        int counter = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                counter++;
//                checkSqlSyntax(line); //TODO
            }

            assertEquals(65,counter );
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
            LOG.info("Error deleting file", e);
        }
    }

}