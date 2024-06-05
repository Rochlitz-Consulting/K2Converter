package org.rochlitz.K2Converter;

import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;

public class RecordCountAggregationStrategy implements AggregationStrategy
{
    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        Integer counter = 1;
        if (oldExchange != null) {
            Integer oldCounter = oldExchange.getIn().getHeader("recordCount", Integer.class);
            if (oldCounter != null) {
                counter = oldCounter + 1;
            }
            oldExchange.getIn().setHeader("recordCount", counter);
            return oldExchange;
        } else {
            newExchange.getIn().setHeader("recordCount", counter);
            return newExchange;
        }
    }
}
