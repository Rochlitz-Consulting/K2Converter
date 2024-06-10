package org.rochlitz.K2Converter.type.converter;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.DefaultExchange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rochlitz.K2Converter.type.record.GenericRecord;

class RecordUnmashallProcessorTest
{

    private RecordUnmashallProcessor processor;
    private Exchange exchange;

    @BeforeEach
    public void setUp() {
        processor = new RecordUnmashallProcessor();
        exchange = new DefaultExchange(new DefaultCamelContext());
    }

    @Test
    void test_mapping_values(){
        String recordValues = getRecordValues();

        processor = new RecordUnmashallProcessor();
        exchange.getIn().setBody(recordValues);
        processor.process(exchange);

        GenericRecord result = exchange.getIn().getBody(GenericRecord.class);

        int size = result.getFields().size();
        assertTrue(size ==135);

    }



    private static String getRecordValues()
    {
        return  "I\r\n"
            + "0100000106\r\n"
            + "02602\r\n"
            + "032\r\n"
            + "041125\r\n"
            + "05\r\n"
            + "062\r\n"
            + "071\r\n"
            + "081\r\n"
            + "09\r\n"
            + "111\r\n"
            + "120\r\n"
            + "15\r\n"
            + "160\r\n"
            + "17\r\n"
            + "18508\r\n"
            + "1920010115\r\n"
            + "2020010115\r\n"
            + "2120210115\r\n"
            + "22\r\n"
            + "230\r\n"
            + "240\r\n"
            + "251\r\n"
            + "26ABSINTHIUM D12\r\n"
            + "27\r\n"
            + "280\r\n"
            + "29\r\n"
            + "30\r\n"
            + "31ABSINTHIUM D 12 Globuli\r\n"
            + "32\r\n"
            + "3360\r\n"
            + "340\r\n"
            + "350\r\n"
            + "361\r\n"
            + "371\r\n"
            + "401\r\n"
            + "43\r\n"
            + "4436245\r\n"
            + "45GLO\r\n"
            + "46\r\n"
            + "47BX01\r\n"
            + "48ABSINTHIUM00000000000000000072\r\n"
            + "491\r\n"
            + "501\r\n"
            + "512\r\n"
            + "522\r\n"
            + "530\r\n"
            + "541\r\n"
            + "552\r\n"
            + "562\r\n"
            + "572\r\n"
            + "582\r\n"
            + "591\r\n"
            + "60\r\n"
            + "61\r\n"
            + "62\r\n"
            + "630\r\n"
            + "641\r\n"
            + "65\r\n"
            + "66\r\n"
            + "67\r\n"
            + "682\r\n"
            + "69\r\n"
            + "721\r\n"
            + "7436\r\n"
            + "762\r\n"
            + "771\r\n"
            + "782\r\n"
            + "801\r\n"
            + "811\r\n"
            + "831\r\n"
            + "85\r\n"
            + "86155\r\n"
            + "871\r\n"
            + "880\r\n"
            + "891\r\n"
            + "901\r\n"
            + "911\r\n"
            + "921\r\n"
            + "930\r\n"
            + "941\r\n"
            + "951\r\n"
            + "961\r\n"
            + "97\r\n"
            + "98\r\n"
            + "A00\r\n"
            + "A21\r\n"
            + "A31\r\n"
            + "A50\r\n"
            + "A60\r\n"
            + "A81\r\n"
            + "A91\r\n"
            + "B00\r\n"
            + "B10\r\n"
            + "B2\r\n"
            + "B30\r\n"
            + "B71\r\n"
            + "B81\r\n"
            + "B9\r\n"
            + "C00\r\n"
            + "C10\r\n"
            + "C20\r\n"
            + "C30\r\n"
            + "C40\r\n"
            + "C50\r\n"
            + "C6\r\n"
            + "C71\r\n"
            + "C81\r\n"
            + "D2\r\n"
            + "D31\r\n"
            + "D4\r\n"
            + "D51\r\n"
            + "D6\r\n"
            + "D81\r\n"
            + "D91\r\n"
            + "E10\r\n"
            + "E2\r\n"
            + "E41\r\n"
            + "E5\r\n"
            + "E60\r\n"
            + "E70\r\n"
            + "E8\r\n"
            + "E9110000010696\r\n"
            + "F1\r\n"
            + "F2\r\n"
            + "F30\r\n"
            + "F40\r\n"
            + "F51\r\n"
            + "F61\r\n"
            + "F8\r\n"
            + "F9\r\n"
            + "G00\r\n"
            + "G10\r\n"
            + "G20\r\n"
            + "G30";
    }

}