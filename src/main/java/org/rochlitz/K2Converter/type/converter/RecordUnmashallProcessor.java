package org.rochlitz.K2Converter.type.converter;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.rochlitz.K2Converter.RouteContext;
import org.rochlitz.K2Converter.type.record.GenericRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RecordUnmashallProcessor implements Processor
{

    public static final String CRLF = "\r\n";
    public static final String RECORD_DELIMITER = CRLF + "00";
    public static final int INDEX_END = 2;

    private static final Logger LOG = LoggerFactory.getLogger(RecordUnmashallProcessor.class);

    public void process(Exchange exchange){

        Message message = exchange.getIn();
        String record = message.getBody(String.class);
        GenericRecord genericRecord = map(record);
        message.setBody(genericRecord);

    }

    public GenericRecord map(String record) {
        try
        {
            if (!record.startsWith("00")){
                record = "00"+record;
            }

            Scanner scanner = new Scanner(record);
            int i = 0;

            HashMap<Integer, String> fieldMap = new HashMap<>();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String id = line.substring(0, INDEX_END);
                String value = line.substring(INDEX_END, line.length());

                fieldMap.put(i, value);
                i++;
                LOG.debug(value);
            }



            if(record.startsWith("00I")){//TODO enum
//TODO                validateFieldCount(fieldMap);
            }

            String type = fieldMap.get(0);
            fieldMap.remove(0);
            List<String> columns = fieldMap.values().stream().collect(Collectors.toList());
            return new GenericRecord(type,columns);
        }
        catch (Exception e)
        {
            LOG.error("Error while mapping record: " + record);
            throw new RuntimeException(e);
        }
    }

    private void fillMapWithMissingFields(HashMap<Integer, String> fieldMap, int id)
    {

        int previousId = id - 1;
        if(!fieldMap.containsKey(previousId)){
            fieldMap.put(previousId, "");
            fillMapWithMissingFields(fieldMap, previousId);
        }

    }

    private String removeIDFromLine(String line)
    {
        return line.substring(2);

    }

    private void validateFieldCount(HashMap<Integer, String> fieldValues)
    {
        int expectedSize = RouteContext.getTableInfo().size();
        int actualSize = fieldValues.size();
        if (expectedSize != actualSize)
        {
            LOG.error("Expected " + expectedSize + " fields but got " + actualSize);
            throw new RuntimeException("Expected " + expectedSize + " fields but got " + actualSize);
        }

    }


}



