package org.rochlitz.K2Converter.type.record;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.rochlitz.K2Converter.RouteContext;
import org.rochlitz.K2Converter.type.converter.InsertConverterProcessor;
import org.rochlitz.K2Converter.type.record.types.EndRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EndConverterProcessor implements Processor
{
    private static final Logger LOG = LoggerFactory.getLogger(InsertConverterProcessor.class);


    public void process(Exchange exchange) throws ClassNotFoundException //TODO add   catch
    {

        GenericRecord genericRecord = exchange.getIn().getBody(GenericRecord.class);

        EndRecord endRecord = new EndRecord();
        endRecord.setDRecordCount(Integer.valueOf(genericRecord.getFields().get(0)));
        endRecord.setIRecordCount(Integer.valueOf(genericRecord.getFields().get(1)));
        endRecord.setURecordCount(Integer.valueOf(genericRecord.getFields().get(2)));
        endRecord.setTotalRecordCount(Integer.valueOf(genericRecord.getFields().get(3)));

        Integer countInserts = RouteContext.getCountInserts();
        Integer expectedIRecords = endRecord.getIRecordCount();

        LOG.info("{} count insert records: {} ",RouteContext.getTableName(), countInserts);
        LOG.info("{} expected count insert records: {} ",RouteContext.getTableName(), expectedIRecords);

        if( countInserts.compareTo(expectedIRecords)!=0){
            throw new RuntimeException("iRecordCount does not match for "+ RouteContext.getTableName());
        }

    }

}
