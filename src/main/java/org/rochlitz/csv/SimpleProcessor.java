package org.rochlitz.csv;

import java.util.UUID;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
 

public class SimpleProcessor implements Processor {
  public void process(Exchange exchange) throws Exception {
      User user = (User) exchange.getIn().getBody();
      user.setId(UUID.randomUUID().toString());
      exchange.getIn().setBody(user);
    
  }
}