package org.rochlitz.K2Converter.toTypeConverter.unmarshall;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.rochlitz.K2Converter.toTypeConverter.GenericRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RecordUnmashallProcessor implements Processor
{

    public static final String CRLF = "\r\n";
    public static final String RECORD_DELIMITER = CRLF + "00";

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
            HashMap<Integer, String> fields = new HashMap();//TODO use list

            record = record.replace("'","\\'");
            int i = 0;
            while (true) {
                boolean endReached=false;
                int beginIndex = record.indexOf(CRLF.concat( String.format("%02d", i + 1)));
                int endIndex =  record.indexOf(CRLF.concat(String.format("%02d", i + 2)));
                if(beginIndex==-1){
                    break;
                }
                if(endIndex==-1){
                    endReached=true;
                }
                if (endReached) {
                    fields.put(i, record.substring(beginIndex).replace("\r\n", "").substring(2));
                }else{
                    fields.put(i, record.substring(beginIndex, endIndex).replace("\r\n", "").substring(2));
                }
                i++;
            }
            LOG.debug("fields: " +fields);

            String type = "K";

            //TODO only the first recod starts with 00 because if .split(body().tokenize(CRLF + "00")) in K2Converter
            if(!record.startsWith("00K")){
                type = record.substring(0, 1);
            }
            List<String> columns = fields.values().stream().collect(Collectors.toList());//TODO test correct order
            return new GenericRecord(type,columns);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}



