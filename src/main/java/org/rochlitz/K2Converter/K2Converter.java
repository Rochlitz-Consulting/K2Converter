package org.rochlitz.K2Converter;

import static org.rochlitz.K2Converter.Configuration.ABDA_DIR_PATH;
import static org.rochlitz.K2Converter.Configuration.DB_SCHMEA_NAME;
import static org.rochlitz.K2Converter.Configuration.SQL_FILE_PATH;
import static org.rochlitz.K2Converter.type.converter.RecordUnmashallProcessor.RECORD_DELIMITER;

import org.apache.camel.Exchange;
import org.apache.camel.Expression;
import org.apache.camel.builder.RouteBuilder;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.rochlitz.K2Converter.out.SqlToFileWriter;
import org.rochlitz.K2Converter.sql.converter.FeldToSqlConverter;
import org.rochlitz.K2Converter.sql.converter.InsertToSqlConverter;
import org.rochlitz.K2Converter.sql.converter.KopfToSqlConverter;
import org.rochlitz.K2Converter.type.converter.FeldConverterProcessor;
import org.rochlitz.K2Converter.type.converter.InsertConverterProcessor;
import org.rochlitz.K2Converter.type.converter.KopfConverterProcessor;
import org.rochlitz.K2Converter.type.converter.RecordUnmashallProcessor;
import org.rochlitz.K2Converter.type.record.EndConverterProcessor;
import org.rochlitz.K2Converter.type.record.GenericRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class K2Converter extends RouteBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(K2Converter.class);


    @Override
    public void configure()  {

        final String abdaDirPath =  System.getProperty(ABDA_DIR_PATH);


        from("file:"+ abdaDirPath.trim() +"?noop=true")
            .log("Processing file: ${header.CamelFileName}")
            .split(splitRecords())
            .process(new RecordUnmashallProcessor())
            .choice()
            .when(this::isTypeOfKopf)//TODO convert to Kopf Type
            .process(new KopfConverterProcessor())
            .process(new KopfToSqlConverter())
            .process(new SqlToFileWriter())
            .when(this::isTypeOfFeld)
            .process(new FeldConverterProcessor())
            .process(new FeldToSqlConverter())
            .process(new SqlToFileWriter())
            .when(this::isTypeOfInsert)
            .process(new InsertConverterProcessor())
            .process(new InsertToSqlConverter())
            .process(new SqlToFileWriter())
//TODO            .to("file:data/output?fileName=output.sql") //TODO
            .when(this::isTypeOfEnd)
            .process(new EndConverterProcessor())
            .process(this::getStatistic)
//            .to("file:data/outox")
            .to("log:processed")
            .end()
        ;
        //TODO log statistic at the end

//TODO add E record

    }//TODO Camel RouteMetrics: add monitoring CPU, Memory

    private void getStatistic(Exchange exchange)
    {
        LOGGER.info("Completed conversion with {} records.", RouteContext.getCountInserts());
    }


    public  Expression splitRecords() {

        return body().tokenize(RECORD_DELIMITER);
    }


    boolean isTypeOfFeld(Exchange exchange)
    {
        GenericRecord body = exchange.getIn().getBody(GenericRecord.class);
        return body.getType().startsWith("F");
    }

    boolean isTypeOfKopf(Exchange exchange)
    {
        GenericRecord body = exchange.getIn().getBody(GenericRecord.class);
        return body.getType().startsWith("K");
    }

    boolean isTypeOfEnd(Exchange exchange)
    {
        GenericRecord body = exchange.getIn().getBody(GenericRecord.class);
        return body.getType().startsWith("E");
    }

    boolean isTypeOfInsert(Exchange exchange)
    {
        GenericRecord body = exchange.getIn().getBody(GenericRecord.class);
        return body.getType().startsWith("I");
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

        String inputPath = cmd.getOptionValue("input", "data/input");
        String db = cmd.getOptionValue("database", "laien_info");
        String outputFile = cmd.getOptionValue("output", "data/outbox/apda.sql");

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
