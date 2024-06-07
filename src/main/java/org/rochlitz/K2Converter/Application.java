package org.rochlitz.K2Converter;

import static org.rochlitz.K2Converter.Configuration.ABDA_DIR_PATH;
import static org.rochlitz.K2Converter.Configuration.DB_SCHMEA_NAME;
import static org.rochlitz.K2Converter.Configuration.SQL_FILE_PATH;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application   {

    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);



    public static void main(String[] args) throws Exception {
        System.out.println("Starting K2Converter");
        parseConfig(args);

        CamelContext context = new DefaultCamelContext();
        context.addRoutes(new K2Converter());
//        context.getPropertiesComponent().setLocation("classpath:k2.properties");
        context.start();
        Thread.sleep(5000);
        context.stop();
        System.out.println("K2Converter finished");
    }


    private static void parseConfig(String[] args)
    {


        Options options = defineOptions();
        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("K2Converter parsing args failed.", options);
            System.exit(1);
            return;
        }

        String inputPath = cmd.getOptionValue("input", "abda");
        String db = cmd.getOptionValue("database", "laien_info");
        String outputFile = cmd.getOptionValue("output", "apda.sql");

        // Setzen Sie die Systemeigenschaften
        System.setProperty(ABDA_DIR_PATH, inputPath);
        System.setProperty(DB_SCHMEA_NAME, db);
        System.setProperty(SQL_FILE_PATH, outputFile);

        printCurrentConfiguration();


    }


    public static void printCurrentConfiguration() {

        System.out.println("Usage java -jar [jar filename] [OPTIONS]");
        System.out.println(" -i=foldername      path to input dir ");
        System.out.println(" -d=schema           name of db schema");
        System.out.println(" -o=sqlfile          name of sql output file");

        String inputPath = System.getProperty(ABDA_DIR_PATH, "abda");
        String db = System.getProperty(DB_SCHMEA_NAME, "laien_info");
        String outputFile = System.getProperty(SQL_FILE_PATH, "apda.sql");

        System.out.println("Current Configuration:");
        System.out.println("Input Path: " + inputPath);
        System.out.println("Database: " + db);
        System.out.println("Output File: " + outputFile);
    }


    private static Options defineOptions() {
        Options options = new Options();

        Option input = new Option("i", "input", true, "input path");
        input.setRequired(false);
        options.addOption(input);

        Option database = new Option("d", "database", true, "database");
        database.setRequired(false);
        options.addOption(database);

        Option output = new Option("o", "output", true, "output file");
        output.setRequired(false);
        options.addOption(output);

        return options;
    }
}
